package com.java;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class User {
    public static void main(String[] args)  {

        Scanner scan = new Scanner(System.in);
        List<Order> list=new ArrayList<>();
        OrderManagementImpl impl = new OrderManagementImpl();
        File file = impl.getFile();
        if(file.length()!=0)
            list = impl.addToList();
        if(list.size()!=0){
            impl.viewOrder();
        }
        boolean error =true;
        while (error) {

            System.out.println("====================================================================================================================================================");
            System.out.println("*****Order Management System*****");
            System.out.println("\t1.Add Order\n \t2.View Order List\n \t3.View Order By Id\n \t4.Sort Order\n\t" +
                                "5.Delete Order By Id\n\t6.Mark as Delivered\n\t7.Generate Report\n\t8.Exit");
            System.out.println("====================================================================================================================================================");

            int choice;

                        try {
                            System.out.print("Enter Option:");
                            choice = scan.nextInt();
                        }catch (InputMismatchException em){
                            System.err.println("Restart and Enter Valid option Between 1 to 8");
                            break;
                        }

            switch (choice) {
                case 1: //Add Order

                        while(true) {
                            String s;
                            do{
                                try{

                                    System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
                                    System.out.println("Enter Order Details:");
                                    System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");

                                    System.out.print("Order Id:");
                                    String orderId = scan.next();
                                    System.out.print("Order Description:");
                                    String orderDes = scan.next();
                                    System.out.print("Delivery Address:");
                                    String deliveryAddress = scan.next();
                                    DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd  hh:mm:ss a");
                                    LocalDateTime now = LocalDateTime.now();
                                    String date = FORMATTER.format(now);
                                    System.out.println("Order Date:" + date);
                                    System.out.print("Amount:");
                                    double amount = scan.nextDouble();
                                    System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
                                    String delDate = "---";
                                    if(list.size()!=0){
                                        boolean exist = false;
                                        Iterator<Order> itr = list.listIterator();
                                            do {
                                                Order order = itr.next();
                                                if (orderId.equals(order.getOrderId())) {
                                                    exist = true;
                                                    break;
                                                }
                                            } while (itr.hasNext());
                                            if(exist){
                                                System.out.println("!!!Duplicate Order Id. Please enter unique order id!!!");
                                                s = "y";

                                            }
                                            else{
                                                list.add( new Order(orderId, orderDes, deliveryAddress, date, amount,delDate));
                                                impl.addOrder(list);
                                                System.out.println("Order Added Successfully");
                                                do{
                                                    System.out.println("Do you want to enter more order details(Y/N):");
                                                    s = scan.next();
                                                    s = s.toUpperCase();
                                                    if(s.length()>1)
                                                        System.out.println("Enter Valid Option");
                                                    else {
                                                        if (s.charAt(0) == 'N')
                                                            break;
                                                        else if (s.charAt(0) == 'Y') {
                                                            break;
                                                        }
                                                        else{
                                                            System.out.println("Enter Valid Option:");
                                                        }
                                                    }
                                                }while (true);
                                            }

                                    }else{
                                        list.add( new Order(orderId, orderDes, deliveryAddress, date, amount,delDate));
                                        impl.addOrder(list);
                                        System.out.println("Order Added Successfully");
                                        do{
                                            System.out.println("Do you want to enter more order details(Y/N):");
                                            s = scan.next();
                                            s = s.toUpperCase();
                                            if(s.length()>1)
                                                System.out.println("Enter Valid Option");
                                            else {
                                                if (s.charAt(0) == 'N')
                                                    break;
                                                else if (s.charAt(0) == 'Y') {
                                                    break;
                                                }
                                                else{
                                                    System.out.println("Enter Valid Option");
                                                }
                                            }
                                        }while (true);

                                    }
                                    break;
                                }catch (InputMismatchException e){
                                    System.out.println("Enter valid Input");
                                    scan.nextLine();
                                }
                            }while(true);

                            if (s.charAt(0) == 'N')
                                break;

                        }
                    break;

                case 2: //View Order List
                                if(file.length()!=0)
                                list = impl.addToList();
                                if(list.size()!=0) {
                                    list = impl.viewOrder();
                                }else{
                                    System.out.println("Order list is empty________!");
                                }
                        break;

                case 3: // View By OrderId
                                if(list.size()!=0){
                                    System.out.print("Enter Order Id:");
                                    String odrId = scan.next();
                                    impl.viewOrder(list,odrId);
                                }else{
                                    System.out.println("Order list is empty________!");
                                }
                        break;

                case 4: // Sort Orders
                                if(list.size()!=0){
                                    impl.sorOrder(list);
                                }else{
                                    System.out.println("Order list is empty________!");
                                }

                        break;

                case 5: // Delete Order By Id
                                if(list.size()!=0) {
                                    String s;
                                   do{
                                       System.out.print("Enter Order Id:");
                                       String ord = scan.next();
                                       impl.deleteById(ord);
                                       
                                        //Auto Exit After List Is Empty
                                       list=impl.addToList();
                                       if(list.size()==0){
                                           System.out.println("Order list is empty");
                                           break;
                                       }

                                       do {
                                           System.out.println("Do you want to delete another order(Y/N):");
                                           s = scan.next();
                                           s = s.toUpperCase();
                                           if(s.length()>1)
                                               System.out.println("Enter Valid Option");
                                           else {
                                               if (s.charAt(0) == 'N')
                                                   break;
                                               else if (s.charAt(0) == 'Y') {
                                                   break;
                                               }
                                               else{
                                                   System.out.println("Enter Valid Option");
                                               }
                                           }
                                       }while (true);
                                       if (s.charAt(0) == 'N')
                                           break;
                                   }while(true);
                                }else{
                                    System.out.println("Order list is empty____!");
                                }

                        break;

                case 6: // Mark as Delivered
                                impl.markDelivered(list);
                        break;

                case 7: // Generate Report
                                if(list.size()!=0)
                                    impl.generateReport();
                                 else
                                    System.out.println("Order list is empty____!");
                        break;

                case 8: // Exit
                                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
                                System.out.println("......Thank You......");
                                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------");
                                error = false;

                        break;

                default: //Invalid Option
                                System.out.println("Enter Valid Option");
            }
        }
    }

}
