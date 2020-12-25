package com.hisense.hiatmp.asn.v2x.api;

import com.hisense.hiatmp.asn.v2x.AsnV2x;

import java.io.IOException;

/**
 * @author zhangyong
 * @date 2020/12/23  21:24
 */
public interface UperAsnProcess {
    /**
     * ��������: <br>
     * <p>UPER ASN����</p>
     * �޸�����: <br>
     * <p>�޸���:</p>
     * <p>�޸�ԭ��:</p>
     *
     * @param in     ������
     * @param type   v2x��Ϣ����
     * @param isUper �Ƿ�Ϊuper���� trueΪ��uper��������
     * @return: T
     * @since: 1.0.0
     * @Author: zhangyong
     * @Date: 2020/11/6 19:49
     */
    AsnV2x decode(byte[] in, AsnV2x.Type type, boolean isUper) throws IOException;
}
