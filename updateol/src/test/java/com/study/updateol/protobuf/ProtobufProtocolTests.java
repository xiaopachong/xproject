package com.study.updateol.protobuf;

import com.study.updateol.utils.ProtobufTestUtils;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.*;

/**
 * protobuf协议测试用例
 */
public class ProtobufProtocolTests {

    @Test
    public void testBackwardCompatible() {
        try {
            ProtobufTestUtils.serializationWithOldProtocol();
            ProtobufTestUtils.deserializationWithNewProtocol();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void testForwardCompatible() {
        try {
            ProtobufTestUtils.serializationWithNewProtocol();
            ProtobufTestUtils.deserializationWithOldProtocol();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
