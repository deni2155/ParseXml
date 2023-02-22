package search;

import folders.InterfaceMovePkg;
import folders.MovePkg;
import java.io.File;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//
//Выполняю поиск пакетов
//
class ListPackage {
    protected ArrayList<File> linkPkg=new ArrayList<>();//массив для хранения ссылок на пакеты

    ListPackage(){
        searchPackage();
    }

    //
    //Запускаю поиск пакетов
    //
    private void searchPackage(){
        //ссылка на класс для логирования
        Logger logging=LoggerFactory.getLogger(ListPackage.class);
        
        try{
            //ссылка на класс для работы с каталогами
            InterfaceMovePkg folders= new MovePkg();
            /*
            *Читаю входящие пакеты
            */
            //XmlFile searchXml=new XmlFile();
            //получаю список каталогов и файлов виде массива
            File[] dir=folders.getInFolder().listFiles();
            //иду по всем объектам массива
            for(int i=0;i<dir.length;i++){
                //если объект является каталогом
                if(dir[i].isDirectory()){
                    //запускаю поиск xml-файла
                    //searchXml=new XmlFile();
                    //searchXml.searchXml(new File(dir[i].getAbsolutePath()));
                    linkPkg.add(dir[i]);//добавляю найденные объекты в массив
                }
            }
            /*
            *Читаю исходящие пакеты
            */
            dir=folders.getOutFolder().listFiles();
            //иду по всем объектам массива
            for(int i=0;i<dir.length;i++){
                //если объект является каталогом
                if(dir[i].isDirectory()){
                    //запускаю поиск xml-файла
                    //searchXml=new XmlFile();
                    //searchXml.searchXml(new File(dir[i].getAbsolutePath()));
                    linkPkg.add(dir[i]);//добавляю найденные объекты в массив
                }
            }
        }
        catch(Exception ex) {
            logging.error("Программная ошибка при поиске пакетов:",ex);
        }
    }
}
