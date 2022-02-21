package com.java;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OrderManagementImpl implements OrderManagement, Runnable{
    private static final String path = "D:\\Order.txt";
    private static final String path1 = "D:\\Order report.txt";
    Scanner scan = new Scanner(System.in);
    @Override
    public void addOrder(List<Order> list) {
        try{
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for(Order o:list) {
                oos.writeObject(o);
            }
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Order> viewOrder(){
        List<Order> list = new ArrayList<>();
        try{
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Order order;
            while(true){
                try{
                    order = (Order) ois.readObject();
                    list.add(order);
                }catch (EOFException e){
                    break;
                }
            }
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("Order Id \t \t | Order Desc \t \t | Address \t \t \t | Order Date \t \t \t \t \t | Amount  \t \t \t | Delivery Datetime");
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
            for(Order ordList : list) {
                System.out.println(ordList.getOrderId().concat("     ").substring(0, 5) + " \t \t \t | " +
                        ordList.getOrderDescription().concat("     ").substring(0, 5) + " \t \t \t | " +
                        ordList.getAddress().concat("     ").substring(0, 5) + " \t \t \t | " + ordList.getOrderDate() + " \t \t | "
                        + ordList.getAmount() + " \t \t \t | " +
                        ordList.getDeliveryDateTime());
            }
            fis.close();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void viewOrder(List<Order> list, String orderId) {
        Order o = null;
        for(Order or:list){
            if(orderId.equals(or.getOrderId())) {
                o = or;
            }
        }
//        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
        if(o==null) {
            System.out.println("Order with orderId "+ orderId +" Not Exist");
        }else {
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("Order Details");
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("OderID\t\t\t : " + o.getOrderId());
            System.out.println("Oder Desc\t\t : " + o.getOrderDescription());
            System.out.println("Delivery Address : " + o.getAddress());
            System.out.println("Order Date\t\t : " + o.getOrderDate());
            System.out.println("Amount\t\t\t : " + o.getAmount());
            System.out.println("Delivery Date\t : " + o.getDeliveryDateTime());
        }
    }

    @Override
    public void sorOrder(List<Order> list) {

        while(true) {
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("*****Choose Sort Order Property*****");
            System.out.println("\t1.OrderId\n \t2.Order Description\n \t3.Delivery Address\n \t4.Order Date\n\t" +
                    "5.Amount\n\t6.Delivery Time\n\t7.Main Menu");
            int opt = 0;
            try {
                System.out.print("Enter Option:");
                opt = new Scanner(System.in).nextInt();
            } catch (InputMismatchException em) {
                System.err.println("Input Mismatch!!!!!");
            }
            if (opt == 1) {
                list.sort((o1, o2) -> (o2.getOrderId().toLowerCase().compareTo(o1.getOrderId().toLowerCase())) * -1);
                sortedList("Order Id", list);
            } else if (opt == 2) {
                list.sort((o1, o2) -> (o2.getOrderDescription().toLowerCase().compareTo(o1.getOrderDescription().toLowerCase())) * -1);
                sortedList("Order Description", list);
            } else if (opt == 3) {
                list.sort((o1, o2) -> (o2.getAddress().toLowerCase().compareTo(o1.getAddress().toLowerCase())) * -1);
                sortedList("Delivery Address", list);
            } else if (opt == 4) {
                list.sort((o1, o2) -> ((o2.getOrderDate().compareTo(o1.getOrderDate()))) * -1);
                sortedList("Order Date", list);
            } else if (opt == 5) {
                list.sort(Comparator.comparingDouble(Order::getAmount));
                sortedList("Amount",list);
            }else if(opt==6) {
                try {
                    list.sort((o1, o2) -> {
                        if (o1.getDeliveryDateTime() != null && o2.getDeliveryDateTime() != null) {
                            return (o2.getDeliveryDateTime().compareTo(o1.getDeliveryDateTime())*-1);
                        } else {
                            return 0;
                        }
                    });
                    sortedList("Delivery Datetime",list);
                } catch (NullPointerException e) {
                    System.out.println("Please Complete All deliveries____");
                }
            }else if(opt==7)
                break;
            else {
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println("Choose Option between 1 to 7");
            }
        }

    }

    @Override
    public void deleteById(String orderId) {
        List<Order> list;
        list = addToList();
        Order o;
        int count = 0;
        for(Order or:list){
            if(orderId.equals(or.getOrderId())) {
                o = or;
                list.remove(o);
                System.out.println("Order Deleted Successfully");
                count++;
                break;
                }
            }

        try {
            new FileWriter(path, false).close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Emptying Exception");
        }
        addOrder(list);
        if(count==0){
            System.out.println("Order with OrderId "+orderId +" does not exist...!");
        }
    }

    @Override
    public void markDelivered(List<Order> list) {
        String s;
        if(list.size()!=0){
            do {
                System.out.print("Enter Order Id:");
                String odrId = scan.next();
                Order order123 = getOrderById(odrId);
                if (order123 != null) {
                    if (order123.getDeliveryDateTime().equals("---")) {
//                        System.out.println("into delivery");
                        DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd  hh:mm:ss a");
                        LocalDateTime now = LocalDateTime.now();
                        String date = FORMATTER.format(now);
//                        System.out.println("Order Date:" + FORMATTER.format(now));
                        order123.setDeliveryDateTime(date);
                        list = deliver(odrId);
                        list.add(order123);
                        list.sort((o1, o2) -> (o2.getOrderId().toLowerCase().compareTo(o1.getOrderId().toLowerCase())) *-1);
                        addOrder(list);
                        System.out.println("Successfully Delivered on:"+date);
                    } else {
                        System.out.println("Order is already delivered on :" + order123.getDeliveryDateTime());
                    }
                } else {
                    System.out.println("Order with id " + odrId + " does not exist...!");
                }
                do {
                    System.out.println("Do you want to mark another Order as Delivered(Y/N):");
                    s = scan.next();
                    s = s.toUpperCase();
                    if (s.length() > 1)
                        System.out.println("Enter Valid Option");
                    else {
                        if (s.charAt(0) == 'N')
                            break;
                        else if (s.charAt(0) == 'Y') {
                            break;
                        } else {
                            System.out.println("Enter Valid Option");
                        }
                    }
                } while (true);
            } while (s.charAt(0) != 'N');
        }else {
            System.out.println("Order list is empty________!");
        }
    }

    @Override
    public void generateReport() {
            run();
    }

    public List<Order> addToList(){
        List<Order> list = new ArrayList<>();
        try{
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Order order;
            while(true){
                try{
                    order = (Order) ois.readObject();
                    list.add(order);
                }catch (EOFException e){
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void sortedList(String s, List<Order> list){
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Successfully Sorted by "+s+":");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Order Id \t \t | Order Desc \t \t | Address \t \t \t | Order Date \t \t \t \t | Amount  \t \t \t | Delivery Datetime");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");

        for(Order ordList : list)
        {
            System.out.println(ordList.getOrderId().concat("     ").substring(0, 5) + " \t \t \t | " +
                    ordList.getOrderDescription().concat("     ").substring(0, 5) + " \t \t \t | " +
                    ordList.getAddress().concat("     ").substring(0, 5) + " \t \t \t | " + ordList.getOrderDate() + " \t | "
                    + ordList.getAmount() + " \t \t \t | " +
                    ordList.getDeliveryDateTime());
        }
    }

    public File getFile(){
        return new File(path);
    }

    public Order getOrderById(String orderId){
        List<Order> list = addToList();
        Order o = null;
        for(Order or:list){
            if(orderId.equals(or.getOrderId())) {
                o = or;
            }
        }
        return o;
    }

    public List<Order> deliver(String orderId){
        List<Order> list;
        list = addToList();
        Order o;
        for(Order or:list){
            if(orderId.equals(or.getOrderId())) {
                o = or;
                list.remove(o);
                break;
            }
        }
        try {
            new FileWriter(path, false).close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Emptying Exception");
        }
        addOrder(list);
        return list;
    }


    @Override
    public void run() {
        List<Order> list = addToList();
        File file = new File(path1);
        try {
            FileWriter fwrite = new FileWriter(path1);
            if(file.exists()){
                file.delete();
            }
            DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd  hh:mm:ss a");
            LocalDateTime now = LocalDateTime.now();
            String date = FORMATTER.format(now);
            fwrite.write("================================================================\n");
            fwrite.write(file.getName()+" :: Order_Report "+date+"\n");
            fwrite.write("================================================================\n");

            for (Order order : list) {
                if (!order.getDeliveryDateTime().equals("---")) {
                    fwrite.write("-----------------------------------------------------\n");
                    fwrite.write("Order Id\t  : " + order.getOrderId() + "\n");
                    fwrite.write("Order Description : " + order.getOrderDescription() + "\n");
                    fwrite.write("Delivery Address  : " + order.getAddress() + "\n");
                    fwrite.write("Order Date\t  : " + order.getOrderDate() + "\n");
                    fwrite.write("Amount\t \t  : " + order.getAmount() + "\n");
                    fwrite.write("Delivery Datetime : " + order.getDeliveryDateTime() + "\n");
                    fwrite.write("-----------------------------------------------------\n");
                }
            }
            System.out.println("Report Generated Successfully");
            fwrite.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Order report error");
        }
    }
}
