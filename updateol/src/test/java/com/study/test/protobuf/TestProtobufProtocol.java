package com.study.test.protobuf;

import com.study.protobuf.newprotocol.ProtobufProtocol;
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
        List<ProtobufProtocol.Person> list = new ArrayList<ProtobufProtocol.Person>();
        ProtobufProtocol.Person person = ProtobufProtocol.Person.newBuilder()
                .setEmail(email_test).setName(name_test).setId(id_test).build();
        list.add(person);
        ProtobufProtocol.AddressBook addressBook = ProtobufProtocol.AddressBook.newBuilder()
                .addAllPerson(list).build();
        System.out.println(addressBook);

        //将测试对象写到本地文件
        ObjectOutputStream outputStream = null;
        try {
            File file = new File(file_path_test);
            outputStream = new ObjectOutputStream(new FileOutputStream(file));
            outputStream.writeObject(addressBook);
            outputStream.flush();
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    @Test
    @DisplayName("使用新协议版本，反序列化本地测试对象")
        //新协议，旧协议必须使用同一个包名,同一个类名，否则会报java.lang.ClassCastException.
    void testDeserializationWithNewProtocol() throws IOException, ClassNotFoundException {
        //读本地文件对象
        ObjectInputStream ois = null;
        try {
            File file = new File(file_path_test);
            ois = new ObjectInputStream(new FileInputStream(file));
            //使用新版本协议
            ProtobufProtocol.AddressBook addressBook =
                    (ProtobufProtocol.AddressBook) ois.readObject();
            assert addressBook != null;
            assert addressBook.getPersonList().size() > 0 && addressBook.getPersonList().get(0) != null;
            assert addressBook.getPersonList().get(0).getId() == id_test;
            assert addressBook.getPersonList().get(0).getEmail().equals(email_test);
            assert addressBook.getPersonList().get(0).getName().equals(name_test);
            System.out.println(addressBook);
        } finally {
            if (ois != null) {
                ois.close();
            }
        }
    }
}
