package folders;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MovePkg extends Folders implements InterfaceMovePkg{
    //путь для перемещения пакетов
    private File folderForMove=null;
    //создаю ссылку на класс для логирования
    private final Logger logger=LoggerFactory.getLogger(MovePkg.class);
    //
    //Возвращаю путь к входящему пакету
    //Наследую из класса ConfigValue
    //
    @Override
    public File getInFolder(){
        return super.getInFolder();
    }


    //
    //Возвращает путь к каталогу с иходящими пакетами
    //Наследую из класса ConfigValue
    //
    @Override
    public File getOutFolder(){
        return super.getOutFolder();
    }

    //
    //Проверяю существование каталогов, указанных в конфигурационном файле
    //Наследую из класса Folders
    //
    @Override
    public boolean getCheckFolder(){
        return super.getCheckFolder();
    }

    @Override
    public void moveError(File linkPkg){
        try{
            if(linkPkg.getParentFile().equals(getInFolder())){
                folderForMove=new File(String.format("%s/%s",getInArchiveErrorFolder(),linkPkg.getName()));//путь для перемещения каталога
                //
                //если в архиве такой каталог уже есть
                //
                if(folderForMove.exists()){
                    folderForMove=new File(String.format("%s/%s%s",getInArchiveErrorFolder(),linkPkg.getName(),RandomLetters()));//путь для перемещения каталога
                }
                FileUtils.moveDirectory(linkPkg, folderForMove);
            }
            //если пакет исходящий
            else if (linkPkg.getParentFile().equals(getOutFolder())){
                folderForMove=new File(String.format("%s/%s",getOutArchiveErrorFolder(),linkPkg.getName()));//путь для перемещения каталога
                //
                //если в архиве такой каталог уже есть
                //
                if(folderForMove.exists()){
                    folderForMove=new File(String.format("%s/%s%s",getOutArchiveErrorFolder(),linkPkg.getName(),RandomLetters()));//путь для перемещения каталога
                }
                FileUtils.moveDirectory(linkPkg, folderForMove);
            }
            logger.warn(String.format("Пакет МЭДО %s не содержит xml-файлы", linkPkg.getPath()));
        }
        catch(IOException ex){
            logger.error(String.format("Программная ошибка при перемещении пакета %s в архив с ошибками", linkPkg),ex);
        }
        
    }

    //
    //Перемещение найденных квитанций
    //
    @Override
    public void moveReceipt(File linkPkg){
        try{
            if(linkPkg.getParentFile().equals(getInFolder())){
                folderForMove=new File(String.format("%s/%s",getInArchiveReceiptFolder(),linkPkg.getName()));//путь для перемещения каталога
                //
                //если в архиве такой каталог уже есть
                //
                if(folderForMove.exists()){
                    folderForMove=new File(String.format("%s/%s%s",getInArchiveReceiptFolder(),linkPkg.getName(),RandomLetters()));//путь для перемещения каталога
                }
                FileUtils.moveDirectory(linkPkg, folderForMove);
            }
            else if (linkPkg.getParentFile().equals(getOutFolder())){
                folderForMove=new File(String.format("%s/%s",getOutArchiveReceiptFolder(),linkPkg.getName()));//путь для перемещения каталога
                //
                //если в архиве такой каталог уже есть
                //
                if(folderForMove.exists()){
                    folderForMove=new File(String.format("%s/%s%s",getOutArchiveReceiptFolder(),linkPkg.getName(),RandomLetters()));//путь для перемещения каталога
                }
                FileUtils.moveDirectory(linkPkg, folderForMove);
            }
        }
        catch(IOException ex){
            logger.error(String.format("Программная ошибка при перемещении пакета с квитанцией %s в архив", linkPkg),ex);
        }
    }

    //
    //Создаёт случайный набор символов
    //
    private String RandomLetters(){
        String letters="copy";
        Random rnd=new Random();
        for(int i=0;i<15;i++){
            letters+=(char)(rnd.nextInt(26)+'A');
        }
        return letters;
    }
    
}
