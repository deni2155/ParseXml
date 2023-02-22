package search;

import java.io.File;
import java.util.ArrayList;
//
//Интерфейс для реализации класса по поиску xml-файлов
//
public interface InterfaceListXmlFile {
    final ArrayList<File> linkXml=new ArrayList<>();//массив для хранения списка xml-файлов
    public void setListXmlFile();
    public ArrayList getListXmlFile();
}
