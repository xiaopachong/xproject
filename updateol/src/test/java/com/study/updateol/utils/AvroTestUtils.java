package com.study.updateol.utils;

import com.study.updateol.avro.newprotocol.NewAddressBook;
import com.study.updateol.avro.newprotocol.NewCompany;
import com.study.updateol.avro.newprotocol.NewPerson;
import com.study.updateol.avro.oldprotocol.OldAddressBook;
import com.study.updateol.avro.oldprotocol.OldPerson;
import com.study.updateol.protobuf.newprotocol.NewProtobufProtocol;
import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
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
        DatumWriter<OldAddressBook> userDatumWriter = new GenericDatumWriter<OldAddressBook>();
        DataFileWriter<OldAddressBook> dataFileWriter = new DataFileWriter<OldAddressBook>(userDatumWriter);
        dataFileWriter.create(addressBook.getSchema(), new File(FILE_PATH_AVRO_TEST_BACKWARD));
        dataFileWriter.append(addressBook);
        dataFileWriter.flush();
        System.out.println("使用旧协议生成对象，序列化到本地文件.");
        System.out.println(addressBook);
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
        NewCompany newCompany = NewCompany.newBuilder().setAddress(COMPANY_ADDRESS_TEST).setName(COMPANY_NAME_TEST).build();
        List<NewCompany> companyList = new ArrayList<NewCompany>();
        companyList.add(newCompany);
        NewAddressBook addressBook = NewAddressBook.newBuilder().setPersonList(personList).setCompanyList(companyList).build();
        DatumWriter<NewAddressBook> userDatumWriter = new GenericDatumWriter<NewAddressBook>();
        DataFileWriter<NewAddressBook> dataFileWriter = new DataFileWriter<NewAddressBook>(userDatumWriter);
        dataFileWriter.create(addressBook.getSchema(), new File(FILE_PATH_AVRO_TEST_FORWARD));
        dataFileWriter.append(addressBook);
        dataFileWriter.flush();
        System.out.println("使用新协议生成对象，序列化到本地文件.");
        System.out.println(addressBook);
    }

    /**
     * 旧协议读取本地文件，解析对象
     *
     * @throws IOException
     */
    public static void deserializationWithOldProtocol() throws IOException {
        DatumReader<OldAddressBook> reader = new GenericDatumReader<OldAddressBook>();
        DataFileReader<OldAddressBook> dataFileReader = new DataFileReader<OldAddressBook>(new File(FILE_PATH_AVRO_TEST_FORWARD), reader);
        System.out.println("使用旧协议从本地文件读取对象");
        while (dataFileReader.hasNext()) {
            System.out.println( dataFileReader.next());
        }
    }

    /**
     * 新协议读取本地文件，解析对象
     *
     * @throws IOException
     */
    public static void deserializationWithNewProtocol() throws IOException {
        DatumReader<NewAddressBook> reader = new GenericDatumReader<NewAddressBook>();
        DataFileReader<NewAddressBook> dataFileReader = new DataFileReader<NewAddressBook>(new File(FILE_PATH_AVRO_TEST_BACKWARD), reader);
        System.out.println("使用新协议从本地文件读取对象");
        while (dataFileReader.hasNext()) {
            System.out.println( dataFileReader.next());
        }
    }

}
