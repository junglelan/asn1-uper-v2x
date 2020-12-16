package com.hisense.hiatmp.asn.v2x.basic;

import net.gcdc.asn1.uper.UperEncoder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhangyong
 * @date 2020/12/15  16:36
 */
public class PathHistoryTest {
    PathHistory pathHistory;

    @Before
    public void setUp() throws Exception {
        FullPositionVector fullPositionVector = new FullPositionVector();
        List<Boolean> gs = Arrays.asList(true, true, true, true, false, false, false, false);
        GNSSstatus gnsSstatus = new GNSSstatus(gs);
        List<PathHistoryPoint> pathHistoryPoints = new ArrayList<>();
        //positionOffsetLL
        PositionOffsetLL.OffsetLLB12 offsetLLB12 = new PositionOffsetLL.OffsetLLB12(22);
        PositionOffsetLL.OffsetLLB12 offsetLLB121 = new PositionOffsetLL.OffsetLLB12(21);
        final PositionOffsetLL.PositionLL24B positionLL24B = new PositionOffsetLL.PositionLL24B(offsetLLB12,
                offsetLLB121);
        PositionOffsetLL positionOffsetLL = PositionOffsetLL.positionLL1(positionLL24B);
        //verticalOffset
        VerticalOffset.VertOffset_B07 vertOffsetB07 = new VerticalOffset.VertOffset_B07(15);
        VerticalOffset verticalOffset = VerticalOffset.offset1(vertOffsetB07);

        //-----------
        PositionOffsetLLV positionOffsetLLV = new PositionOffsetLLV(positionOffsetLL, verticalOffset);

        pathHistoryPoints.add(new PathHistoryPoint(positionOffsetLLV,
                new DDateTime.TimeOffset(633),
                new Speed(500), null, new CoarseHeading(20)));
        PathHistory.PathHistoryPointList pathHistoryPointList = new PathHistory.PathHistoryPointList(pathHistoryPoints);

        pathHistory = new PathHistory(null, gnsSstatus, pathHistoryPointList);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void encode() throws Exception {
        //62, 0, 88, -127, 104, 21, 19, -64, -98, 67, -24, 40
        //62, 0, 88, -127, 104, 21, 19, -64, -98, 3, -24, 40
        final byte[] encode = UperEncoder.encode(pathHistory);
        System.out.println(Arrays.toString(encode));

    }

    @Test
    public void decode() throws Exception {
    }
}