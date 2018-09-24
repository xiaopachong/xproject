package com.study.updateol.avro;

import com.study.updateol.utils.AvroTestUtils;
import org.junit.Test;

import java.io.IOException;

/**
 * @Auther: liuxin
 * @Date: 2018/9/24 20:11
 * @Description: avro协议测试用例
 */
public class AvroProtocolTests {

    @Test
    public void testBackwardCompatible() {
        try {
            AvroTestUtils.serializationWithOldProtocol();
            AvroTestUtils.serializationWithNewProtocol();
            AvroTestUtils.deserializationWithNewProtocol();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testForwardCompatible() {
        try {
            AvroTestUtils.serializationWithNewProtocol();
            AvroTestUtils.deserializationWithOldProtocol();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

