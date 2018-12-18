package com.bignerdranch.android.twohealtest;


import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class DBUtil {
    private ArrayList<String> arrayList = new ArrayList<String>();
    private ArrayList<String> brrayList = new ArrayList<String>();
    private ArrayList<String> crrayList = new ArrayList<String>();

    private HttpConnSoap Soap = new HttpConnSoap();

    public static Connection getConnection() {
        Connection con = null;
        try {
            //Class.forName("org.gjt.mm.mysql.Driver");
            //con=DriverManager.getConnection("jdbc:mysql://192.168.0.106:3306/test?useUnicode=true&characterEncoding=UTF-8","root","initial");
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return con;
    }
    //  selectAllCompetitonInfo
    public class ThirdThread implements Callable<ArrayList<String>>{
        public ArrayList<String> call(){
            crrayList = Soap.GetWebServre ("selectAllCompetitonInfo", arrayList, brrayList);
            return crrayList;
        }
    }

    /**
     * 获取所有货物的信息
     *
     * @return
     */
    public List<HashMap<String, String>> getAllInfo() {
        List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        arrayList.clear();
        brrayList.clear();
        crrayList.clear();


        ThirdThread rt = new ThirdThread(); //创建Callable对象
        FutureTask<ArrayList<String>> task = new FutureTask<ArrayList<String>>(rt);//使用FutrueTask来包装Callable对象
        new Thread(task, "有返回值的线程").start(); //实际上还是以Callable对象创建并启动线程
        try {
            crrayList = task.get();
        } catch (InterruptedException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }

//		new Thread() {
//			public void run()
//			{
//				try{
//					crrayList = Soap.GetWebServre ("selectAllCargoInfor", arrayList, brrayList);
//				}
//				catch(Exception e) {
//
//				}
//			}
//		}.start(); 	//多线程Callable和Future


        HashMap<String, String> tempHash = new HashMap<String, String>();
        tempHash.put("Cno", "Cno");
        tempHash.put("CTitle", "全国2018数学建模竞赛");
        tempHash.put("CStatus", "正在报名");
        tempHash.put("CHost", "北京理工大学");
        tempHash.put("CLevel", "国家级");
        tempHash.put("CTime", "2018.06.26~2018.08.09");
        tempHash.put("CTimng", "2018.06.26~2018.08.09");
        list.add(tempHash);

        for (int j = 0; j < crrayList.size(); j += 7) {
            HashMap<String, String> hashMap = new HashMap<String, String>();
            hashMap.put("Cno", crrayList.get(j));
            hashMap.put("CTitle", crrayList.get(j + 1));
            hashMap.put("CStatus", crrayList.get(j + 2));
            hashMap.put("CHost", crrayList.get(j + 3));
            hashMap.put("CLevel", crrayList.get(j + 4));
            hashMap.put("CTime", crrayList.get(j + 5));
            hashMap.put("CTimng", crrayList.get(j + 6));
            list.add(hashMap);
        }

        return list;
    }

    /**
     * 增加一条货物信息
     *
     * @return
     */
    public void insertCompetitionInfo(String CTitle, String CStatus, String CHost, String CLevel, String CTime, String CTimng) {

        arrayList.clear();
        brrayList.clear();

        arrayList.add("CTitle");
        arrayList.add("CStatus");
        arrayList.add("CHost");
        arrayList.add("CLevel");
        arrayList.add("CTime");
        arrayList.add("CTimng");
        brrayList.add(CTitle);
        brrayList.add(CStatus);
        brrayList.add(CHost);
        brrayList.add(CLevel);
        brrayList.add(CTime);
        brrayList.add(CTimng);

        new Thread(){
            public void run()
            {
                try{
                    Soap.GetWebServre("insertCompetitionInfo", arrayList, brrayList);
                }
                catch(Exception e) {
                }
            }
        }.start();
        //Soap.GetWebServre("insertCargoInfo", arrayList, brrayList);
    }

    /**
     * 删除一条货物信息
     *
     * @return
     */
    public void deleteCompetitonInfo(String Cno) {

        arrayList.clear();
        brrayList.clear();

        arrayList.add("Cno");
        brrayList.add(Cno);

        new Thread(){
            public void run()
            {
                try{
                    Soap.GetWebServre("deleteCompetitonInfo", arrayList, brrayList);
                }
                catch(Exception e) {
                }
            }
        }.start();

    }
}
