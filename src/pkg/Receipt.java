package pkg;

import folders.InterfaceMovePkg;
import folders.MovePkg;
import java.io.File;
import java.io.IOException;
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

public class Receipt extends AbstractClass{
    private String forUid="";//UID пакета, для которого получена квитанция
    private String delivery="";//информация о получении документа (получен\не получен)
    private String comment="";//комментарий
    private String orgReceiver="";//организация получатель

    public Receipt(File pathXml, String versionPkg, String typePkg, String uidReceivedPkg, String orgSender) {
        super(pathXml, versionPkg, typePkg, uidReceivedPkg, orgSender);
    }

    @Override
    void parsingByType(){
        Logger logger=LoggerFactory.getLogger(Receipt.class);
        try{
            
            System.out.println(String.format("Путь к пакету:%s",getPathXml()));
            System.out.println(String.format("Версия:%s",getVersionPkg()));
            System.out.println(String.format("Тип:%s",getTypePkg()));
            System.out.println(String.format("UID:%s",getUidReceivedPkg()));
            System.out.println(String.format("Отправитель:%s",getOrgSender()));

            //создание ссылки на фабрику для работы с с билдером документов
            DocumentBuilderFactory factoryXml=DocumentBuilderFactory.newInstance();
            //указываю, что используются префиксы
            factoryXml.setNamespaceAware(true);
            //из ссылки на фабрику  создаю билдер, который выполняет парсинг файлов в иерархическом виде
            DocumentBuilder builderXml=factoryXml.newDocumentBuilder();
            //читаю документ
            Document doc=builderXml.parse(getPathXml());
            //нормализую документ для чтения
            doc.normalize();
            //получаю корневой элемент
            //получаю простанство имён, используемое в xml
            Element eElement=(Element) doc.getDocumentElement();
            String namespace=eElement.getNamespaceURI();
            //получаю тэг acknowledgment с использованием пространства имён
            NodeList nNodeList=doc.getElementsByTagNameNS(namespace,"acknowledgment");
            Node nNode=null;
            //если тэг acknowledgment существует 
            if(nNodeList.getLength()>0){
                //перебираем все его элементы
                for(int ack=0;ack<nNodeList.getLength();ack++){
                    nNode=nNodeList.item(ack);
                    //если есть дочерние элементы
                    if(nNode.getNodeType()==Node.ELEMENT_NODE){
                        eElement=(Element)nNode;
                        //получем версию xml-файла
                        if(eElement.hasAttributeNS(namespace, "uid") && !eElement.getAttributeNS(namespace, "uid").equals("")){
                            forUid=eElement.getAttributeNS(namespace, "uid");
                            System.out.println(String.format("Получен для пакета:%s",forUid));
                        }
                        else{
                            logger.warn(String.format("В xml-файле %s тег communication\\acknowledgment не содержит атрибут uid",getPathXml()));
                        }
                        //получаю тэг accepted с использованием пространства имён
                        NodeList nListAccept=eElement.getElementsByTagNameNS(namespace, "accepted");
                        if(nListAccept.getLength()>0){
                            for(int accept=0;accept<nListAccept.getLength();accept++){
                                Node nNodeAccept=nListAccept.item(accept);
                                //если есть дочерние элементы
                                if(nNodeAccept.getNodeType()==Node.ELEMENT_NODE){
                                    eElement=(Element)nNodeAccept;
                                    delivery=eElement.getTextContent();
                                }
                                else{
                                logger.warn(String.format("В xml-файле %s тег communication\\acknowledgment\\accepted не содержит дочерние элементы",getPathXml()));
                                }
                            }
                        }
                        else{
                            logger.warn(String.format("В xml-файле %s не найден тег communication\\acknowledgment\\accepted",getPathXml()));
                        }
                    }
                    else{
                        logger.warn(String.format("В xml-файле %s тег communication\\acknowledgment не содержит дочерние элементы",getPathXml()));
                    }

                    //получаю тэг comment с использованием пространства имён
                    eElement=(Element)nNode;
                    NodeList nListComm=eElement.getElementsByTagNameNS(namespace, "comment");
                    if(nListComm.getLength()>0){
                        for(int comm=0;comm<nListComm.getLength();comm++){
                            Node nNodeComm=nListComm.item(comm);
                            //если есть дочерние элементы
                            if(nNodeComm.getNodeType()==Node.ELEMENT_NODE){
                                eElement=(Element)nNodeComm;
                                comment=eElement.getTextContent();    
                            }
                            else{
                                logger.warn(String.format("В xml-файле %s тег communication\\acknowledgment\\comment не содержит дочерние элементы",getPathXml()));
                            }
                        }
                    }
                    else{
                        logger.warn(String.format("В xml-файле %s не найден тег communication\\acknowledgment\\comment",getPathXml()));
                    }
                }
            }
            else{
                logger.warn(String.format("В xml-файле %s не найден тег communication\\acknowledgment",getPathXml()));
            }
            //
            //Получатель пакета
            //
            //получаю тэг deliveryIndex с использованием пространства имён
            nNodeList=doc.getElementsByTagNameNS(namespace,"deliveryIndex");
            if(nNodeList.getLength()>0){
                for(int delIndex=0;delIndex<nNodeList.getLength();delIndex++){
                    nNode=nNodeList.item(delIndex);
                    //если есть дочерние элементы
                    if(nNode.getNodeType()==Node.ELEMENT_NODE){
                        eElement=(Element)nNode;
                        //
                        //Захожу в тег destination
                        //
                        nNodeList=eElement.getElementsByTagNameNS(namespace, "destination");
                        if(nNodeList.getLength()>0){
                            for(int dest=0;dest<nNodeList.getLength();dest++){
                                nNode=nNodeList.item(dest);
                                if(nNode.getNodeType()==Node.ELEMENT_NODE){
                                    eElement=(Element)nNode;
                                    //
                                    //Захожу во второй destination
                                    //
                                    nNodeList=eElement.getElementsByTagNameNS(namespace, "destination");
                                    if(nNodeList.getLength()>0){
                                        for(int destin=0;destin<nNodeList.getLength();destin++){
                                            nNode=nNodeList.item(destin);
                                            if(nNode.getNodeType()==Node.ELEMENT_NODE){
                                                eElement=(Element)nNode;
                                                //
                                                //Захожу в организатион
                                                //
                                                nNodeList=eElement.getElementsByTagNameNS(namespace, "organization");
                                                if(nNodeList.getLength()>0){
                                                    for(int org=0;org<nNodeList.getLength();org++){
                                                        nNode=nNodeList.item(org);
                                                        if(nNode.getNodeType()==Node.ELEMENT_NODE){
                                                            eElement=(Element)nNode;
                                                            orgReceiver=eElement.getTextContent();
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            else{
                logger.warn(String.format("В xml-файле %s не найден тег communication\\deliveryIndex",getPathXml()));
            }

            System.out.println(String.format("доставка:%s",delivery));
            System.out.println(comment);
            System.out.println(orgReceiver);
            System.out.println("");
            InterfaceMovePkg move=new MovePkg();
            move.moveReceipt(new File(getPathXml().getParent()));
        }
        catch(IOException | SAXException | ParserConfigurationException ex){
            logger.error(String.format("Программная ошибка при парсинге xml-файла %s:",getPathXml(), ex));
        }     
    }
}
