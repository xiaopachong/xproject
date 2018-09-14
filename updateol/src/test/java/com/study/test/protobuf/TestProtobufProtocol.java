package com.study.test.protobuf;

import com.study.protobuf.newprotocol.NewProtobufProtocol;
import com.study.protobuf.oldprotocol.OldProtobufProtocol;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 测试protobuf协议.
 */
public class TestProtobufProtocol {

    //测试用数据
    private String email_test = "a@c.1";
    private String name_test = "hell";
    private int id_test = 93;

    //序列化文件路径
    private String file_path_test = "T_person";

    @Test
    @DisplayName("序列化protobuf对象到本地文件")
    void testSerializationWithOldProtocol() throws IOException {
        //构造测试对象
        List<OldProtobufProtocol.Person> list = new ArrayList<OldProtobufProtocol.Person>();
        OldProtobufProtocol.Person person = OldProtobufProtocol.Person.newBuilder()
                .setEmail(email_test).setName(name_test).setId(id_test).build();
        list.add(person);
        OldProtobufProtocol.AddressBook addressBook = OldProtobufProtocol.AddressBook.newBuilder()
                .addAllPerson(list).build();
        System.out.println(addressBook);

        //将测试对象写到本地文件
        OutputStream os = null;
        try {
            File file = new File(file_path_test);
            os = new FileOutputStream(file);
            os.write(addressBook.toByteArray());
            os.flush();
        } finally {
            if (os != null) {
                os.close();
            }
        }
    }

    @Test
    @DisplayName("使用新协议版本，反序列化本地测试对象")
    void testDeserializationWithNewProtocol() throws IOException {
        //读本地文件对象
        InputStream is = null;
        try {
            File file = new File(file_path_test);
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
            System.out.println(addressBook);
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }
}
