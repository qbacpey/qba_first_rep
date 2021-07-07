import Service.Service;

import java.util.Scanner;

public class View {
    //绑定一个Service
    private static Service aService;
    //新建一个Scanner用来读取所用用户输入
    private Scanner aScanner = new Scanner(System.in);

    private void initView() {
        aService = new Service();
    }

    public static void main(String[] args) {
        View temp = new View();
        temp.initView();
        while (true) {
            temp.total_table();
        }
    }

    //用来和用户交互，根据用户输入调用Service中相应函数
    public void total_table() {
        System.out.println("欢迎来到录像店");
        System.out.println("0.DVD排行");
        System.out.println("1.新增DVD");
        System.out.println("2.查看DVD");
        System.out.println("3.删除DVD");
        System.out.println("4.借出DVD");
        System.out.println("5.归还DVD");
        System.out.println("请选择：");
        switch (aScanner.nextInt()) {
            case 0 -> {
                System.out.println("*********************");
                System.out.println("|租借次数       名称   ");
                System.out.println(aService.rentTime());
                System.out.println("*********************");
            }
            case 1 -> {
                System.out.println("****************************************************");
                System.out.println("请输入DVD的名称：");
                String tempName1 = aScanner.next();
                System.out.println("请输入DVD的租金");
                aService.addVideo(tempName1, aScanner.nextDouble());
                System.out.println("添加成功");
                System.out.println("****************************************************");
            }
            case 2 -> {
                System.out.println("****************************************************");
                System.out.println("|序号       状 态       名称                 借出日期   ");
                System.out.println(aService.scanVideo());
                System.out.println("****************************************************");
                ;
            }
            case 3 -> {
                System.out.println("****************************************************");
                System.out.println("请输入DVD的名称：");
                System.out.println(aService.delVideo(aScanner.next()));
                System.out.println("****************************************************");
            }
            case 4 -> {
                System.out.println("****************************************************");
                System.out.println("请输入DVD的名称：");
                String tempName2 = aScanner.next();
                System.out.println("请输入DVD的出借日期(格式:yyyy-MM-dd)");
                System.out.println(aService.rentVideo(tempName2, aScanner.next()));;
                System.out.println("****************************************************");
            }
            case 5 -> {
                System.out.println("****************************************************");
                System.out.println("请输入DVD的名称：");
                String tempName3 = aScanner.next();
                System.out.println("请输入DVD的归还日期(格式:yyyy-MM-dd)");
                System.out.println(aService.backVideo(tempName3, aScanner.next()));;
                System.out.println("****************************************************");
            }
        }

    }
}
