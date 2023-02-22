package ru.scrooge;

import folders.InterfaceMovePkg;
import folders.MovePkg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pkg.Pkg;
import search.InterfaceListXmlFile;
import search.ListXmlFile;
/*
* основной класс
*/
public class MainClass {

    public static void main(String[] args) {
        //создаю ссылку на класс для логирования
        Logger logger=LoggerFactory.getLogger(MainClass.class);
        
        try{
            InterfaceMovePkg folders=new MovePkg();
            //
            //если конфигурационный файл корректно сформирован (все каталоги указаны и существуют)
            //
            if(folders.getCheckFolder()){
                InterfaceListXmlFile listXml=new ListXmlFile();
                listXml.setListXmlFile();//записываю списко xml-файлов в массив
                Pkg pkg=new Pkg();
                pkg.listForParse(listXml.getListXmlFile());//передаю xml-файлы для парсинга
            }
            else{
                logger.warn("Необходимо исправить ошибки в конфигурационном файле приложения");
            }
        }
        catch(Exception ex){
            logger.error("Программная ошибка при поиске файлов и каталогов МЭДО:\n{}", ex);
        }
    }
}
