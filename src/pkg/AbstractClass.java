package pkg;

import java.io.File;

public abstract class AbstractClass {
    private final File pathXml;//путь к пакету
    private final String versionPkg;//версия xml
    private final String typePkg;//тип пакета (квитанция, уведомления, документ и т.д.)
    private final String uidReceivedPkg;//uid пакета
    private final String orgSender;//uid отправителя пакета

    protected File getPathXml() {
        return pathXml;
    }

    protected String getVersionPkg() {
        return versionPkg;
    }

    protected String getTypePkg() {
        return typePkg;
    }

    protected String getUidReceivedPkg() {
        return uidReceivedPkg;
    }

    protected String getOrgSender() {
        return orgSender;
    }
    
    public AbstractClass(File pathXml,String versionPkg,String typePkg,String uidReceivedPkg,String orgSender){
        this.pathXml=pathXml;
        this.versionPkg=versionPkg;
        this.typePkg=typePkg;
        this.uidReceivedPkg=uidReceivedPkg;
        this.orgSender=orgSender;
    }

    abstract void parsingByType();
}
