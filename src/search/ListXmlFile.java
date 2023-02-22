package search;

import folders.MovePkg;
import java.io.File;
import java.util.ArrayList;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//
//Выполняю поиск xml-файлов
//
public class ListXmlFile extends ListPackage implements InterfaceListXmlFile {
   
    //
    //Получаю список xml-файлов
    //
    @Override
    public void setListXmlFile(){
        Logger logging=LoggerFactory.getLogger(ListXmlFile.class);
        
        try{
//            Folders folders=new Folders();
            //
            //Если пакеты МЭДО найдены
            //
            File[] file=null;//массив для поиска xml-файлов
            int numXml=0;//число xml-файлов в пакете
            //
            //Если пакеты найдены
            //
            if(!linkPkg.isEmpty()){
                for(int i=0;i<linkPkg.size();i++){
                    numXml=0;
                    file=linkPkg.get(i).listFiles();
                    //
                    //Если в пакете найдены файлы
                    //
                    if(file.length>0){
                        for(int j=0;j<file.length;j++){
                            //ищу xml-файлы в пакетах
                            if("xml".equals(FilenameUtils.getExtension(file[j].getAbsolutePath()))){
                                linkXml.add(file[j]);
                                numXml++;
                            }
                        }

                        //
                        //Если файлы xml-не найдены в пакете
                        //
                        if(numXml==0){
                            //перемещаю в каталог с ошибками
                            MovePkg movePkg=new MovePkg();
                            movePkg.moveError(linkPkg.get(i));
//                            File resultFolderForMove;//путь для перемещения каталога
//                            //если пакет входящий
//                            if(linkPkg.get(i).getParentFile().equals(folders.getInFolder())){
//                                resultFolderForMove=new File(String.format("%s/%s",folders.getInArchiveErrorFolder(),linkPkg.get(i).getName()));//путь для перемещения каталога
//                                //
//                                //если в архиве такой каталог уже есть
//                                //
//                                if(resultFolderForMove.exists()){
//                                    resultFolderForMove=new File(String.format("%s/%s%s",folders.getInArchiveErrorFolder(),linkPkg.get(i).getName(),RandomLetters()));//путь для перемещения каталога
//                                }
//                                FileUtils.moveDirectory(linkPkg.get(i), resultFolderForMove);
//                            }
//                            //если пакет исходящий
//                            else if (linkPkg.get(i).getParentFile().equals(folders.getOutFolder())){
//                                resultFolderForMove=new File(String.format("%s/%s",folders.getOutArchiveErrorFolder(),linkPkg.get(i).getName()));//путь для перемещения каталога
//                                //
//                                //если в архиве такой каталог уже есть
//                                //
//                                if(resultFolderForMove.exists()){
//                                    resultFolderForMove=new File(String.format("%s/%s%s",folders.getOutArchiveErrorFolder(),linkPkg.get(i).getName(),RandomLetters()));//путь для перемещения каталога
//                                }
//                                FileUtils.moveDirectory(linkPkg.get(i), resultFolderForMove);
//                            }
//                            logging.warn(String.format("Пакет МЭДО %s не содержит xml-файлы", linkPkg.get(i).getPath()));
                        }
                    }
                    //
                    //Иначе просто удаляем каталог
                    //
                    else{
                        logging.warn(String.format("Пакет МЭДО %s пустой и не содержит файлы",linkPkg.get(i).getAbsolutePath()));
                        linkPkg.get(i).delete();
                    }
                }
            }
            else{
                logging.info("Не найдены пакеты МЭДО для парсинга");
            }
        }catch(IllegalArgumentException ex) {
            logging.error("Программная ошибка при поиске xml-файлов:",ex);
        }
    }
//    //
//    //Создаёт случайный набор символов
//    //
//    private String RandomLetters(){
//        String letters="copy";
//        Random rnd=new Random();
//        for(int i=0;i<15;i++){
//            letters+=(char)(rnd.nextInt(26)+'A');
//        }
//        return letters;
//    }
    //
    //Возвращаю список xml-файлов
    //
    @Override
    public ArrayList getListXmlFile(){
        return linkXml;
    }
}
