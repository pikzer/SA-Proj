package CheckingApp;


import CheckingApp.services.DataList;

import java.io.FileOutputStream;
import java.io.IOException;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.mail.Authenticator;
import javax.mail.Transport;
import javax.mail.Message;
import javax.mail.Address;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Properties;


public class test {
    private static DataList dataList;

    private static void Excel() throws IOException {
        DataList dataList = new DataList();
        dataList.getBoqFormDataBase();
        dataList.getTorFormDataBase();

        String[] columns = {"BO_Name", "BO_Price", "BO_Amount"};
//        List<BOQ> contacts = new ArrayList<BOQ>();
//        contacts.add(new BOQ("ท่อ", 1000, 1));
//        contacts.add(new BOQ("ท่อ 2", 2000, 1));
//        contacts.add(new BOQ("ท่อ 3", 3000, 1));



        Workbook workbook = new XSSFWorkbook();
        String fileName = dataList.getTorArrayList().get(1).getTO_Name();
        Sheet sheet = workbook.createSheet("fileName");

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 18);
        headerFont.setColor(IndexedColors.BLUE.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        Row headerRow = sheet.createRow(0);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        int rowNum = 1;

        for (BOQ boq : dataList.getBoqArrayList()) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(boq.getBO_Name());
            row.createCell(1).setCellValue(boq.getBO_Amount());
            row.createCell(2).setCellValue(boq.getBO_Price());
        }

// Resize all columns to fit the content size
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        /**
         * Need to create Excel dir
         */
        FileOutputStream fileOut = new FileOutputStream("Excel/"+ fileName+".xlsx");
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
    }

    public static void main(String[] args) throws Exception  {


//        DataList dataList = new DataList();
////        dataList.getBoqFormDataBase();
////        dataList.getTorFormDataBase();
        Excel();

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", 587);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.transport.protocol", "smtp");

        // Create a mail session so you can create and send an email message.
        Session sessions = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("checking.boq@gmail.com", "Code1234");
            }
        });

        Message message = new MimeMessage(sessions);
        /**
         * ข้อความหัว mail
         */
        message.setSubject("BOQ");

        // Set the destination address.
        Address addressTo = new InternetAddress("checking.boq@gmail.com");
        message.setRecipient(Message.RecipientType.TO, addressTo);

        MimeMultipart multipart = new MimeMultipart();

        MimeBodyPart attachment = new MimeBodyPart();
        attachment.attachFile(new File("Excel/asdasd.xlsx"));

        //            MimeBodyPart attachment2 = new MimeBodyPart();
//            attachment2.attachFile(new File("FILE_TO_SEND"));

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent("<h1>Hello! Thank you for reading my first email!</h1>", "text/html");
        multipart.addBodyPart(messageBodyPart);
        multipart.addBodyPart(attachment);

        message.setContent(multipart);
        Transport.send(message);
//            multipart.addBodyPart(attachment2);

//        // If you want to CC someone
//        Address addressCC = new InternetAddress("CCaddress");
//        message.setRecipient(Message.RecipientType.CC);
//
//        // If you want to BCC someone
//        Address addressBCC = new InternetAddress("BCCAddress");
//        message.setRecipient(Message.RecipientType.BCC);

//        // You can also add more recipients
//        Address addressAdd = new InternetAddress("ANOTHER_EMAIL");
//        message.addRecipient(Message.RecipientType.TO, addressAdd);

    }
}