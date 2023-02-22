package folders;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//
//Класс для чтения информации из конфигурационного файла
//
class ConfigValues{
    //
    //Путь к конфигурационному файлу
    //
    private File pathConfigFile=new File(folders.Folders.class.getProtectionDomain().getCodeSource().getLocation().getPath()+"/resources/config.properties");

    File getPathConfigFile() {
        return pathConfigFile;
    }
    
    //
    //Папка с входящей корреспонденцией
    //
    private File in;

    File getIn() {
        return in;
    }
    //
    //Папка с исходящей корреспонденцией
    //
    File out;

    File getOut() {
        return out;
    }
    //
    //Архив пакетов
    //
    private File archive;

    File getArchive() {
        return archive;
    }
    //
    //Временная папка приложения
    //
    private File temp;

    File getTemp() {
        return temp;
    }

    //
    //Читаю конфигурационный файл
    //
    ConfigValues(){
        Logger logger=LoggerFactory.getLogger(ConfigValues.class);
        try{
            //если ссылка пустая на файл конфигурации, все переменные будут равны null
            //т.к. в произвольном классе Folders выполняется проверка переменных на пустоту, если переменные раны null, основной класс остановит выполнение метода main
            //если ссылка не пустая, выполняется считывание ифнормации и её запись в переменные
            if(pathConfigFile.exists() && pathConfigFile!=null){
                //читаю свойства из файла и присваю значения массиву
                FileInputStream fileStream=new FileInputStream(pathConfigFile);
                try{
                    Properties properties=new Properties();
                    properties.load(fileStream);
                    in=new File(properties.getProperty("in"));
                    out=new File(properties.getProperty("out"));
                    archive=new File(properties.getProperty("archive"));
                    temp=new File(properties.getProperty("temp"));
                }catch(IOException ex){
                    logger.error(String.format("При чтении конфигурационного файла %s возникла программная ошибка", pathConfigFile.getAbsolutePath()), ex);
                }finally{
                    fileStream.close();
                }
            }
            //не нвйден конфигурационный файл
            else{
                logger.warn(String.format("Не найден конфигурационный файл %s",pathConfigFile.getAbsolutePath()));
            }
        }
        catch(FileNotFoundException ex){
            logger.error(String.format("При чтении конфигурационного файла %s возникла программная ошибка",pathConfigFile.getAbsolutePath()), ex);
        }
        catch(IOException ex){
            logger.error(String.format("При чтении конфигурационного файла %s возникла программная ошибка",pathConfigFile.getAbsolutePath()), ex);
        }
        catch(Exception ex){
            logger.error(String.format("При чтении конфигурационного файла %s возникла программная ошибка",pathConfigFile.getAbsolutePath()), ex);
        }
    }
}