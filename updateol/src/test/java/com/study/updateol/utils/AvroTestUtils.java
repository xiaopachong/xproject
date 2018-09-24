package com.study.updateol.utils;

import com.study.updateol.avro.newprotocol.NewAddressBook;
import com.study.updateol.avro.newprotocol.NewPerson;
import com.study.updateol.avro.oldprotocol.OldAddressBook;
import com.study.updateol.avro.oldprotocol.OldPerson;
import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.message.SchemaStore;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import javax.xml.bind.DatatypeConverter;

import static com.study.updateol.generator.DataGenerator.*;

import java.io.*;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: liuxin
 * @Date: 2018/9/24 20:11
 * @Description: avro协议测试工具
 */
public class AvroTestUtils {

    /**
     * 旧协议生成对象，写入本地文件
     *
     * @throws IOException
     */
    public static void serializationWithOldProtocol() throws IOException {
        List<OldPerson> personList = new ArrayList<OldPerson>();
        OldPerson oldPerson = OldPerson.newBuilder().setEmail(EMAIL_TEST).setId(ID_TEST).setName(NAME_TEST).build();
        personList.add(oldPerson);
        OldAddressBook addressBook = OldAddressBook.newBuilder().setPersonList(personList).build();

    /*    DatumWriter<OldAddressBook> userDatumWriter = new SpecificDatumWriter<OldAddressBook>(OldAddressBook.class);
        DataFileWriter<OldAddressBook> dataFileWriter = new DataFileWriter<OldAddressBook>(userDatumWriter);
        dataFileWriter.create(addressBook.getSchema(),  new File(FILE_PATH_AVRO_TEST_BACKWARD));*/
        System.out.println(new String(new BigInteger(1, addressBook.toByteBuffer().array()).toString(10)));
        System.out.println(DatatypeConverter.printHexBinary(addressBook.toByteBuffer().array()));
        System.out.println(new String(addressBook.toByteBuffer().array()));
        //将测试对象写到本地文件
        OutputStream os = null;
        try {
            File file = new File(FILE_PATH_AVRO_TEST_BACKWARD);
            os = new FileOutputStream(file);
            os.write(addressBook.toByteBuffer().array());
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
        List<NewPerson> personList = new ArrayList<NewPerson>();
        NewPerson newPerson = NewPerson.newBuilder().setEmail(EMAIL_TEST).setId(ID_TEST).setName(NAME_TEST).build();
        personList.add(newPerson);
//        NewAddressBook newAddressBook = NewAddressBook.newBuilder().setPersonList(personList).build();
        NewAddressBook newAddressBook = new NewAddressBook();
        newAddressBook.setPersonList(personList);

        System.out.println("111111111111");
        System.out.println(DatatypeConverter.printHexBinary(newAddressBook.toByteBuffer().array()));


        System.out.println(new String(new BigInteger(1, newAddressBook.toByteBuffer().array()).toString(10)));

        //将测试对象写到本地文件
        OutputStream os = null;
        try {
            File file = new File(FILE_PATH_AVRO_TEST_FORWARD);
            os = new FileOutputStream(file);
            os.write(newAddressBook.toByteBuffer().array());
            os.flush();
            System.out.println("使用新协议生成对象，序列化到本地文件.");
            System.out.println(newAddressBook);
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
            File file = new File(FILE_PATH_AVRO_TEST_FORWARD);
            is = new FileInputStream(file);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int len = 0;
            while ((len = is.read(b)) != -1) {
                os.write(b, 0, len);
            }
            //使用旧版本协议
            OldAddressBook addressBook =
                    OldAddressBook.fromByteBuffer(ByteBuffer.wrap(os.toByteArray()));
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
//        DatumReader<NewAddressBook> reader = new SpecificDatumReader<NewAddressBook>(NewAddressBook.class);
        //将schema从newStringPair.avsc文件中加载
        Schema.Parser parser = new Schema.Parser();
        Schema newSchema = parser.parse(Thread.currentThread().getClass().getResourceAsStream("/protocol/avro/new/test"));

//        DatumReader<NewAddressBook> reader = new GenericDatumReader<NewAddressBook>(null, newSchema);
//        DataFileReader<NewAddressBook> dataFileReader = new DataFileReader<NewAddressBook>(new File(FILE_PATH_AVRO_TEST_BACKWARD), reader);
//        System.out.println(dataFileReader.getHeader());
//        System.out.println(dataFileReader.getSchema());
//        NewAddressBook user = null;
//        while (dataFileReader.hasNext()) {
//            user = dataFileReader.next();
//            if(user == null) {
//                System.out.println(1);
//            }
//            System.out.println(user);
//        }

        //读本地文件对象
        InputStream is = null;
        try {
            File file = new File(FILE_PATH_AVRO_TEST_BACKWARD);
            is = new FileInputStream(file);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int len;
            while ((len = is.read(b)) != -1) {
                os.write(b, 0, len);
            }
            System.out.println(DatatypeConverter.printHexBinary(os.toByteArray()));
            //使用新版本协议
//            NewAddressBook addressBook =
//                    NewAddressBook.fromByteBuffer(ByteBuffer.wrap(os.toByteArray()));
            SchemaStore.Cache cache = new SchemaStore.Cache();
            cache.addSchema(newSchema);
            NewAddressBook addressBook = NewAddressBook.createDecoder(cache).decode(is);
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
