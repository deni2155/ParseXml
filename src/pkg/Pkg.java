package pkg;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Pkg {
        public void listForParse(ArrayList<File> xmlFile){
        Logger logger=LoggerFactory.getLogger(Pkg.class);
        try{
            if(!xmlFile.isEmpty()){
                for(int i=0;i<xmlFile.size();i++){
                    startParse(xmlFile.get(i));
                }
            }
        }
        catch(Exception ex){
            logger.error("Программная ошибка при чтении массива со списком xml-файлов",ex); 
        }
    }

    private void startParse(File xmlFile){
        Logger logger=LoggerFactory.getLogger(Pkg.class);
        AbstractClass byType=null;
        try {
            String versionPkg="";//версия xml
            String typePkg="";//тип пакета (квитанция, уведомления, документ и т.д.)
            String uidReceivedPkg="";//uid пакета
//            String uidOrgSender="";//uid отправителя пакета
            String orgSender="";//организация отправитель

            //создание ссылки на фабрику для работы с с билдером документов
            DocumentBuilderFactory factoryXml=DocumentBuilderFactory.newInstance();
            //указываю, что используются префиксы
            factoryXml.setNamespaceAware(true);
            //из ссылки на фабрику  создаю билдер, который выполняет парсинг файлов в иерархическом виде
            DocumentBuilder builderXml=factoryXml.newDocumentBuilder();
            //читаю документ
            Document doc=builderXml.parse(xmlFile);
            //нормализую документ для чтения
            doc.normalize();
            //получаю корневой элемент
            //получаю простанство имён, используемое в xml
            Element eElement=(Element) doc.getDocumentElement();
            String namespace=eElement.getNamespaceURI();
            //получаю тэг communication с использованием пространства имён
            NodeList nNodeList=doc.getElementsByTagNameNS(namespace,"communication");
            Node nNode=null;
            //если тэг communication существует 
            if(nNodeList.getLength()>0){
                //перебираем все его элементы
                for(int comm=0;comm<nNodeList.getLength();comm++){
                    nNode=nNodeList.item(comm);
                    //если есть дочерние элементы
                    if(nNode.getNodeType()==Node.ELEMENT_NODE){
                        eElement=(Element)nNode;

                        //если есть атрибуты и они не пустые
                        //получем версию xml-файла
                        if(eElement.hasAttributeNS(namespace, "version") && !eElement.getAttributeNS(namespace, "version").equals("")){
                            versionPkg=eElement.getAttributeNS(namespace, "version");
                        }
                        else{
                            logger.info(String.format("В xml-файле %s не найден атрибут version тега communication",xmlFile.getAbsoluteFile()));
                        }

                        //
                        //Захожу в тег header
                        //
                        nNodeList=eElement.getElementsByTagNameNS(namespace, "header");
                        if(nNodeList.getLength()>0){
                            for(int head=0;head<nNodeList.getLength();head++){
                                nNode=nNodeList.item(head);
                                //если есть дочерние элементы
                                if(nNode.getNodeType()==Node.ELEMENT_NODE){
                                    eElement=(Element)nNode;

                                    //ищу тип пакета
                                    if(eElement.hasAttributeNS(namespace,"type")){
                                        typePkg=eElement.getAttributeNS(namespace, "type");
                                    }
                                    else{
                                        logger.info(String.format("В xml-файле %s не найден атрибут type тега communication\\head",xmlFile.getAbsoluteFile()));
                                    }
                                    //ищу UID пакета
                                    if(eElement.hasAttributeNS(namespace,"uid")){
                                        uidReceivedPkg=eElement.getAttributeNS(namespace, "uid");
                                    }
                                    else{
                                        logger.info(String.format("В xml-файле %s не найден атрибут uid тега communication\\head",xmlFile.getAbsoluteFile()));
                                    }

                                    //
                                    //Захожу в тег sorce
                                    //
                                    nNodeList=eElement.getElementsByTagNameNS(namespace, "source");
                                    if(nNodeList.getLength()>0){
                                        for(int source=0;source<nNodeList.getLength();source++){
                                            nNode=nNodeList.item(source);

                                            //если есть дочерние элементы
                                            if(nNode.getNodeType()==Node.ELEMENT_NODE){
                                                //
                                                //Захожу в тег организацитион
                                                //
                                                nNodeList=eElement.getElementsByTagNameNS(namespace, "organization");
                                                if(nNodeList.getLength()>0){
                                                    for(int org=0;org<nNodeList.getLength();org++){
                                                        nNode=nNodeList.item(org);
                                                        //если есть дочерние элементы
                                                        if(nNode.getNodeType()==Node.ELEMENT_NODE){
                                                            eElement=(Element)nNode;
                                                            orgSender=eElement.getTextContent();
                                                        }
                                                        else{
                                                            logger.warn(String.format("В xml-файле %s не найдены дочерние элементы для тега communication\\head\\source\\organization",xmlFile.getAbsoluteFile()));
                                                        }
                                                    }
                                                }
                                                else{
                                                    logger.warn(String.format("В xml-файле %s не найден тег communication\\head\\source\\organization",xmlFile.getAbsoluteFile()));
                                                }
                                            }
                                            else{
                                                logger.warn(String.format("В xml-файле %s не найдены дочерние элементы для тега communication\\head\\source",xmlFile.getAbsoluteFile()));
                                            }
                                        }
                                    }
                                    else{
                                        logger.warn(String.format("В xml-файле %s не найден тег communication\\head\\source",xmlFile.getAbsoluteFile()));
                                    }
                                }
                                else{
                                    logger.info(String.format("В xml-файле %s не найдены дочерние элементы в теге communication\\head",xmlFile.getAbsoluteFile()));
                                }
                            }
                        }else{
                            logger.warn(String.format("В xml-файле %s не найден тег communication\\head",xmlFile.getAbsoluteFile()));
                        }
                    }
                    else{
                        logger.info(String.format("В xml-файле %s не найдены дочерние элементы в теге communication",xmlFile.getAbsoluteFile()));
                    }
                }
            }else{
                logger.warn(String.format("В xml-файле %s не найден тег communication",xmlFile.getAbsoluteFile()));
            }

            if(typePkg.equals("Квитанция")){
                byType=new Receipt(xmlFile,versionPkg, typePkg, uidReceivedPkg, orgSender);
                byType.parsingByType();
            }
            else if(typePkg.equals("Уведомление")){
            }
            else if(typePkg.equals("Документ")){
            }
            else if(typePkg.equals("Транспортный контейнер")){
                
            }
            else{
                logger.warn(String.format("Не определён тип пакета %s",xmlFile.getAbsoluteFile()));
            }
        }
        catch(IOException | ParserConfigurationException | SAXException ex){
            logger.error(String.format("Программная ошибка при парсинге xml-файла %s:",xmlFile.getAbsoluteFile(), ex));
        }
    }
}
