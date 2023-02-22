package folders;

import java.io.File;
//
//Интерфейс реализует класс для перемещения пакетов
//
public interface InterfaceMovePkg {
    //
    //Возвращает путь к каталогу с входящими пакетами
    //
    public File getInFolder();
    //
    //Возвращает путь к каталогу с иходящими пакетами
    //
    public File getOutFolder();
    //
    //Проверяю существование каталогов, указанных в конфигурационном файле
    //
    public boolean getCheckFolder();
    //
    //Пермещение пакетов в ошибки
    //
    public void moveError(File linkPkg);
    //
    //Перемещаю квитанции
    //
    public void moveReceipt(File linkPkg);
}
