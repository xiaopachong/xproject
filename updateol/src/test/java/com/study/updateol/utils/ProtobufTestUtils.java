package com.study.updateol.utils;

import com.study.updateol.protobuf.newprotocol.NewProtobufProtocol;
import com.study.updateol.protobuf.oldprotocol.OldProtobufProtocol;
import static com.study.updateol.generator.DataGenerator.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * protobuf协议测试工具
 */
public class ProtobufTestUtils {

    /**
     * 旧协议生成对象，写入本地文件
     *
     * @throws IOException
     */
    public static void serializationWithOldProtocol() throws IOException {
        //构造测试对象
        List<OldProtobufProtocol.Person> personList = new ArrayList<OldProtobufProtocol.Person>();
        OldProtobufProtocol.Person person = OldProtobufProtocol.Person.newBuilder()
                .setEmail(EMAIL_TEST).setName(NAME_TEST).setId(ID_TEST).build();
        personList.add(person);
        OldProtobufProtocol.AddressBook addressBook = OldProtobufProtocol.AddressBook.newBuilder()
                .addAllPerson(personList).build();

        //将测试对象写到本地文件
        OutputStream os = null;
        try {
            File file = new File(FILE_PATH_PROTOBUF_TEST_BACKWARD);
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
     *
     * @throws IOException
     */
    public static void serializationWithNewProtocol() throws IOException {
        //构造测试对象
        List<NewProtobufProtocol.Person> personList = new ArrayList<NewProtobufProtocol.Person>();
        NewProtobufProtocol.Person person = NewProtobufProtocol.Person.newBuilder()
                .setEmail(EMAIL_TEST).setName(NAME_TEST).setId(ID_TEST).setAge(AGE_TEST).build();
        personList.add(person);
        List<NewProtobufProtocol.Company> companyList = new ArrayList<NewProtobufProtocol.Company>();
        NewProtobufProtocol.Company company = NewProtobufProtocol.Company.newBuilder()
                .setAddress(ADDRESS_TEST).setName(COMPANY_NAME_TEST).build();
        companyList.add(company);
        NewProtobufProtocol.AddressBook addressBook = NewProtobufProtocol.AddressBook.newBuilder()
                .addAllPerson(personList).addAllCompany(companyList).build();

        //将测试对象写到本地文件
        OutputStream os = null;
        try {
            File file = new File(FILE_PATH_PROTOBUF_TEST_FORWARD);
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
     *
     * @throws IOException
     */
    public static void deserializationWithOldProtocol() throws IOException {
        //读本地文件对象
        InputStream is = null;
        try {
            File file = new File(FILE_PATH_PROTOBUF_TEST_FORWARD);
            is = new FileInputStream(file);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int len = 0;
            while ((len = is.read(b)) != -1) {
                os.write(b, 0, len);
            }
            //使用旧版本协议
            OldProtobufProtocol.AddressBook addressBook =
                    OldProtobufProtocol.AddressBook.parseFrom(os.toByteArray());
            assert addressBook != null;
            assert addressBook.getPersonList().size() > 0 && addressBook.getPersonList().get(0) != null;
            assert addressBook.getPersonList().get(0).getId() == ID_TEST;
            assert addressBook.getPersonList().get(0).getEmail().equals(EMAIL_TEST);
            assert addressBook.getPersonList().get(0).getName().equals(NAME_TEST);
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
     *
     * @throws IOException
     */
    public static void deserializationWithNewProtocol() throws IOException {
        //读本地文件对象
        InputStream is = null;
        try {
            File file = new File(FILE_PATH_PROTOBUF_TEST_BACKWARD);
            is = new FileInputStream(file);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int len;
            while ((len = is.read(b)) != -1) {
                os.write(b, 0, len);
            }
            //使用新版本协议
            NewProtobufProtocol.AddressBook addressBook =
                    NewProtobufProtocol.AddressBook.parseFrom(os.toByteArray());
            assert addressBook != null;
            assert addressBook.getPersonList().size() > 0 && addressBook.getPersonList().get(0) != null;
            assert addressBook.getPersonList().get(0).getId() == ID_TEST;
            assert addressBook.getPersonList().get(0).getEmail().equals(EMAIL_TEST);
            assert addressBook.getPersonList().get(0).getName().equals(NAME_TEST);
            System.out.println("使用新协议从本地文件读取对象");
            System.out.println(addressBook);
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }
}
