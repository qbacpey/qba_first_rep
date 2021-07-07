package Service.Impl;

import Service.Inte.IService;

public class Service_Admin implements IService {
    @Override
    public String res() {
        return null;
    }

    @Override
    public String log() {
        return null;
    }

    @Override
    public String del() {
        return null;
    }

    @Override
    public String scan() {
        return null;
    }

    static {

    }

    private static IService aService_Admin;
    private Service_Admin(){}
    public static IService get(){
        if(aService_Admin==null)
            aService_Admin = new Service_Admin();
        return aService_Admin;
    }

}
