package folders;

import java.io.File;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

///
//Класс для работы с каталогами, указанными в конфигурационном файле
//
//public class Folders extends ConfigValues implements InterfaceFolders{
class Folders extends ConfigValues{
    private final LocalDate date=LocalDate.now();//переменная для работы с датой
    //создаю ссылку на класс для логирования
    private final Logger logger=LoggerFactory.getLogger(Folders.class);
    //переменная для создания ссылок на каталоги разного типа
    private File folderTypePackage;

    //
    //Возвращаю путь к каталогу с входящими из класса-наследника
    //
    //@Override
    File getInFolder(){
        return super.getIn();
    }
    //
    //Возвращаю путь к каталогу с исходящими из класса-наследника
    //
    //@Override
    File getOutFolder(){
        return super.getOut();
    }

    //
    //Проверяю существование каталогов, указанных в конфигурационном файле
    //
    //@Override
    boolean getCheckFolder(){
       try{
            //
            //Описываю условие для логирования
            //
            if(getIn()==null){
                logger.warn(String.format("В конфигурационном файле %s не найдено значение переменной in",getPathConfigFile().getAbsolutePath()));
            }
            else if(!getIn().exists()){
                logger.warn(String.format("Не найден каталог, указанный для переменной in в конфигурационном файле %s",getPathConfigFile().getAbsolutePath()));
            }
            else if(getOut()==null){
                logger.warn(String.format("В конфигурационном файле %s не найдено значение переменной out",getPathConfigFile().getAbsolutePath()));
            }
            else if(!getOut().exists()){
                logger.warn(String.format("Не найден каталог, указанный для переменной out в конфигурационном файле %s",getPathConfigFile().getAbsolutePath()));
            }
            else if(getArchive()==null){
                logger.warn(String.format("В конфигурационном файле %s не найдено значение переменной archive",getPathConfigFile().getAbsolutePath()));
            }
            else if(!getArchive().exists()){
                logger.warn(String.format("Не найден каталог, указанный для переменной archive в конфигурационном файле %s",getPathConfigFile().getAbsolutePath()));
            }
            else if(getTemp()==null){
                logger.warn(String.format("В конфигурационном файле %s не найдено значение переменной temp",getPathConfigFile().getAbsolutePath()));
            }
            else if(!getTemp().exists()){
                logger.warn(String.format("Не найден каталог, указанный для переменной temp в конфигурационном файле %s",getPathConfigFile().getAbsolutePath())); 
            }
            //
            //возвращаю значение результата для проверки перед запуском остальных компонентов приложения
            //
            return (getIn()!=null && getIn().exists()) && (getOut()!=null && getOut().exists()) && (getArchive()!=null && getArchive().exists()) && (getTemp()!=null && getTemp().exists());
        }
        catch(Exception ex){
            logger.error(String.format("При проверке существования каталогов, указанных в конфигурационном файле, %s возникла программная ошибка",getPathConfigFile().getAbsolutePath()), ex);
            return false;
        }
    }

    //
    //Входящие документы в архиве
    //
    //@Override
    File getInArchiveDocFolder(){
        folderTypePackage=new File(String.format("%s/in/documents/%s/%s",getArchive().getAbsolutePath(),date.getYear(),date.getMonthValue()));
        if(!folderTypePackage.exists()){
            if(folderTypePackage.mkdirs()){
                logger.info(String.format("Созданно дерево каталогов %s",folderTypePackage.getAbsolutePath()));
            }
        }
        return folderTypePackage;
    }
    //
    //Возвращает путь к каталогу с входящими уведомлениями в архиве
    //
    //@Override
    File getInArchiveNotifFolder(){
        folderTypePackage=new File(String.format("%s/in/notifications/%s/%s",getArchive().getAbsolutePath(),date.getYear(),date.getMonthValue()));
        if(!folderTypePackage.exists()){
            if(folderTypePackage.mkdirs()){
                logger.info(String.format("Созданно дерево каталогов %s",folderTypePackage.getAbsolutePath()));
            }
        }
        return folderTypePackage;
    }
    //
    //Возвращает путь к каталогу с входящими квитанциями в архиве
    //
    //@Override
    File getInArchiveReceiptFolder(){
        folderTypePackage=new File(String.format("%s/in/receipts/%s/%s",getArchive().getAbsolutePath(),date.getYear(),date.getMonthValue()));
        if(!folderTypePackage.exists()){
            if(folderTypePackage.mkdirs()){
                logger.info(String.format("Созданно дерево каталогов %s",folderTypePackage.getAbsolutePath()));
            }
        }
        return folderTypePackage;
    }
    //
    //Возвращает путь к каталогу с входящими пакетами в ошибках
    //
    //@Override
    File getInArchiveErrorFolder(){
        folderTypePackage=new File(String.format("%s/in/errors/%s/%s",getArchive().getAbsolutePath(),date.getYear(),date.getMonthValue()));
        if(!folderTypePackage.exists()){
            if(folderTypePackage.mkdirs()){
                logger.info(String.format("Созданно дерево каталогов %s",folderTypePackage.getAbsolutePath()));
            }
        }
        return folderTypePackage;
    }
    //
    //Возвращаю путь к каталогу с исходящими документами в архиве
    //
    //@Override
    File getOutArchiveDocFolder(){
        folderTypePackage=new File(String.format("%s/out/documents/%s/%s",getArchive().getAbsolutePath(),date.getYear(),date.getMonthValue()));
        if(!folderTypePackage.exists()){
            if(folderTypePackage.mkdirs()){
                logger.info(String.format("Созданно дерево каталогов %s",folderTypePackage.getAbsolutePath()));
            }
        }
        return folderTypePackage;
    }
    //
    //Возвращает путь к каталогу с исходящими уведомлениями в архиве
    //
    //@Override
    File getOutArchiveNotifFolder(){
        folderTypePackage=new File(String.format("%s/out/notifications/%s/%s",getArchive().getAbsolutePath(),date.getYear(),date.getMonthValue()));
        if(!folderTypePackage.exists()){
            if(folderTypePackage.mkdirs()){
                logger.info(String.format("Созданно дерево каталогов %s",folderTypePackage.getAbsolutePath()));
            }
        }
        return folderTypePackage;
    }
    //
    //Возвращает путь к каталогу с исходящими квитанциями в архиве
    //
    //@Override
    File getOutArchiveReceiptFolder(){
        folderTypePackage=new File(String.format("%s/out/receipts/%s/%s",getArchive().getAbsolutePath(),date.getYear(),date.getMonthValue()));
        if(!folderTypePackage.exists()){
            if(folderTypePackage.mkdirs()){
                logger.info(String.format("Созданно дерево каталогов %s",folderTypePackage.getAbsolutePath()));
            }
        }
        return folderTypePackage;
    }
    //
    //Возвращает путь к каталогу с исходящими пакетами в ошибках
    //
    //@Override
    File getOutArchiveErrorFolder(){
        folderTypePackage=new File(String.format("%s/out/errors/%s/%s",getArchive().getAbsolutePath(),date.getYear(),date.getMonthValue()));
        if(!folderTypePackage.exists()){
            if(folderTypePackage.mkdirs()){
                logger.info(String.format("Созданно дерево каталогов %s",folderTypePackage.getAbsolutePath()));
            }
        }
        return folderTypePackage;
    }
}
