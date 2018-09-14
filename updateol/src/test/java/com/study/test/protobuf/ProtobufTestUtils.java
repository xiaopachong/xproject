package com.study.test.protobuf;

import com.study.protobuf.newprotocol.NewProtobufProtocol;
import com.study.protobuf.oldprotocol.OldProtobufProtocol;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * protobuf协议测试
 */
public class ProtobufTestUtils {
    //测试用数据
    private static String email_test = "a@c.1";
    private static String name_test = "hell";
    private static int id_test = 93;
    private static int age_test = 34;

    //序列化文件路径
    private static String file_path_test_backward = "T_AddressBook_backward";
    private static String file_path_test_forward = "T_AddressBook_forward";

    /**
     * 旧协议生成对象，写入本地文件
     * @throws IOException
     */
    static void testSerializationWithOldProtocol() throws IOException {
        //构造测试对象
        List<OldProtobufProtocol.Person> personList = new ArrayList<OldProtobufProtocol.Person>();
        OldProtobufProtocol.Person person = OldProtobufProtocol.Person.newBuilder()
                .setEmail(email_test).setName(name_test).setId(id_test).build();
        personList.add(person);
        OldProtobufProtocol.AddressBook addressBook = OldProtobufProtocol.AddressBook.newBuilder()
                .addAllPerson(personList).build();

        //将测试对象写到本地文件
        OutputStream os = null;
        try {
            File file = new File(file_path_test_backward);
            os = new FileOutputStream(file);
            os.write(addressBook.toByteArray());
            os.flush();
            System.out.println("使用旧协议生成对象，序列化到本地文件.");
            System.out.println(addressBook);
        } finally {
            if (os != null) {
                os.close();
            }
        }
    }

    /**
     * 新协议生成对象，写入本地文件
     * @throws IOException
     */
    static void testSerializationWithNewProtocol() throws IOException {
        //构造测试对象
        List<NewProtobufProtocol.Person> personList = new ArrayList<NewProtobufProtocol.Person>();
        NewProtobufProtocol.Person person = NewProtobufProtocol.Person.newBuilder()
                .setEmail(email_test).setName(name_test).setId(id_test).setAge(age_test).build();
        personList.add(person);
        List<NewProtobufProtocol.Company> companyList = new ArrayList<NewProtobufProtocol.Company>();
        NewProtobufProtocol.Company company = NewProtobufProtocol.Company.newBuilder()
                .setAddress("湖北").setName("电信").build();
        companyList.add(company);
        NewProtobufProtocol.AddressBook addressBook = NewProtobufProtocol.AddressBook.newBuilder()
                .addAllPerson(personList).addAllCompany(companyList).build();

        //将测试对象写到本地文件
        OutputStream os = null;
        try {
            File file = new File(file_path_test_forward);
            os = new FileOutputStream(file);
            os.write(addressBook.toByteArray());
            os.flush();
            System.out.println("使用新协议生成对象，序列化到本地文件.");
            System.out.println(addressBook);
        } finally {
            if (os != null) {
                os.close();
            }
        }
    }

    /**
     * 旧协议读取本地文件，解析对象
     * @throws IOException
     */
    static void testDeserializationWithOldProtocol() throws IOException {
        //读本地文件对象
        InputStream is = null;
        try {
            File file = new File(file_path_test_forward);
            is = new FileInputStream(file);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int len = 0;
            while ((len = is.read(b)) != -1) {
                os.write(b, 0, len);
            }
            //使用新版本协议
            OldProtobufProtocol.AddressBook addressBook =
                    OldProtobufProtocol.AddressBook.parseFrom(os.toByteArray());
            assert addressBook != null;
            assert addressBook.getPersonList().size() > 0 && addressBook.getPersonList().get(0) != null;
            assert addressBook.getPersonList().get(0).getId() == id_test;
            assert addressBook.getPersonList().get(0).getEmail().equals(email_test);
            assert addressBook.getPersonList().get(0).getName().equals(name_test);
            System.out.println("使用旧协议从本地文件读取对象");
            System.out.println(addressBook);
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    /**
     * 新协议读取本地文件，解析对象
     * @throws IOException
     */
    static void testDeserializationWithNewProtocol() throws IOException {
        //读本地文件对象
        InputStream is = null;
        try {
            File file = new File(file_path_test_backward);
            is = new FileInputStream(file);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int len = 0;
            while ((len = is.read(b)) != -1) {
                os.write(b, 0, len);
            }
            //使用新版本协议
            NewProtobufProtocol.AddressBook addressBook =
                    NewProtobufProtocol.AddressBook.parseFrom(os.toByteArray());
            assert addressBook != null;
            assert addressBook.getPersonList().size() > 0 && addressBook.getPersonList().get(0) != null;
            assert addressBook.getPersonList().get(0).getId() == id_test;
            assert addressBook.getPersonList().get(0).getEmail().equals(email_test);
            assert addressBook.getPersonList().get(0).getName().equals(name_test);
            System.out.println("使用新协议从本地文件读取对象");
            System.out.println(addressBook);
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }
}
