# 基于JAVA的ASN.1-UPER在网联车（V2X）消息编解码框架介绍
## 整体项目结构
```
├─asn-binary-notes
├─asn1-datatypes
├─asn1-uper      
├─asn1-v2x
├─asnlab-uper
│                                          
├─dos
│      ASN.1及其UPER编码在LTE中的应用研究_文小强.pdf
│      T-REC-X.694-201508-Mapping W3C XML schema definitions into ASN.1.pdf
│      T-REC-X.695-201508-Registration and application of PER encoding instructions.pdf
│      X.680-0207-Specification of basic notation.pdf
│      X.681-0207-Abstract Syntax Notation One (ASN.1)Information object specification.pdf
│      X.682-0207-Abstract Syntax Notation One (ASN.1) Constraint specification.pdf
│      X.683-0207-Abstract Syntax Notation One (ASN.1) Parameterization of ASN.1 specifications.pdf
│      X.690-0207-asn1-ber-der-cer.pdf
│      X.691-0207-asn1-uper.pdf
│      X.692-0203-Specification of encoding control notation (ECN).pdf
│      X.693-0112-XML encoding rules (XER).pdf
│      X.693-0112.pdf
│      
└─sources
    └─v2x
        │  v2x数据示例.txt
        │  路侧RSU数据对接方案11-23.docx
        │  
        └─Release20190724
                BSM.asn
                DefAcceleration.asn
                DefMotion.asn
                DefPosition.asn
                DefPositionOffset.asn
                DefTime.asn
                Map.asn
                MapLane.asn
                MapLink.asn
                MapNode.asn
                MapPoint.asn
                MapSpeedLimit.asn
                MsgFrame.asn
                RSI.asn
                RSM.asn
                SignalPhaseAndTiming.asn
                SPATIntersectionState.asn
                VehBrake.asn
                VehClass.asn
                VehEmgExt.asn
                VehSafetyExt.asn
                VehSize.asn
                VehStatus.asn
```
## ASN.1的学习、参考文档（算法实现的基础）

[ASN.1-DOC](https://github.com/zyjohn0822/asn1-uper-v2x/tree/master/dos)

## 标准车联网消息集ASN文件参考（包括：BSM、SPAT、RSI、RSM、MAP）

[V2X-ASN-DOC](https://github.com/zyjohn0822/asn1-uper-v2x/tree/master/sources/v2x)


## ASN.1与框架的对应关系

ASN.1 | Java
----- | ----
`INTEGER` (unconstrained) | `BigInteger`
`INTEGER` (constrained)   | `short`, `int`, `long`, `BigInteger` (depending on constraint)
`BOOLEAN`                 | `boolean`
`ENUMERATED`              | `enum`
`SEQUENCE`                | class
`CHOICE`                  | class
`BIT STRING` (constrained to fixed length) | class
`BIT STRING` (non-fixed length)            | `List<Boolean>`
`OCTET STRING`            | `List<Byte>`
`IA5String`, `UTF8String`, `VisibleString` | `String`
`SEQUENCE OF T`           | `List<T>` (or `SequenceOfT extends Asn1SequenceOf<T>`)
`SET OF T`                | also `List<T>`

## 注解介绍
- @Asn1Optional
- @Bitstring
- @Choice
- @Component
- @HasExtensionMarker
- @IntRange
- @OctetString
- @Sequence 
- @SizeRange 

## 框架用例介绍

### Example 1: 基本类型

- INTEGER

```asn.1
MsgCount ::= INTEGER (0..127)
```

对应Java代码：

```java

@IntRange(minValue = 0, maxValue = 127)
public class MsgCount extends Asn1Integer {
    public MsgCount() {
        this(0);
    }

    public MsgCount(int value) {
        super(value);
    }
}
```

- ENUMERATED - Without restrictions or extension markers

```asn.1
PositionConfidence ::= ENUMERATED {
		unavailable (0), -- Not Equipped or unavailable
		a500m (1), -- 500m or about 5 * 10 ^ -3 decimal degrees
		a200m (2), -- 200m or about 2 * 10 ^ -3 decimal degrees
		a100m (3), -- 100m or about 1 * 10 ^ -3 decimal degrees
		a50m (4), -- 50m or about 5 * 10 ^ -4 decimal degrees
		a20m (5), -- 20m or about 2 * 10 ^ -4 decimal degrees
		a10m (6), -- 10m or about 1 * 10 ^ -4 decimal degrees
		a5m (7), -- 5m or about 5 * 10 ^ -5 decimal degrees
		a2m (8), -- 2m or about 2 * 10 ^ -5 decimal degrees
		a1m (9), -- 1m or about 1 * 10 ^ -5 decimal degrees
		a50cm (10), -- 0.50m or about 5 * 10 ^ -6 decimal degrees
		a20cm (11), -- 0.20m or about 2 * 10 ^ -6 decimal degrees
		a10cm (12), -- 0.10m or about 1 * 10 ^ -6 decimal degrees
		a5cm (13), -- 0.05m or about 5 * 10 ^ -7 decimal degrees
		a2cm (14), -- 0.02m or about 2 * 10 ^ -7 decimal degrees
		a1cm (15) -- 0.01m or about 1 * 10 ^ -7 decimal degrees
	}
```

对应的java代码：

```java
public enum PositionConfidence {
    /**
     * -- Not Equipped or unavailable
     */
    unavailable(0),
    /**
     * -- 500m or about 5 * 10 ^ -3 decimal degrees
     */
    a500m(1),
    /**
     * -- 200m or about 2 * 10 ^ -3 decimal degrees
     */
    a200m(2),
    /**
     * -- 100m or about 1 * 10 ^ -3 decimal degrees
     */
    a100m(3),
    /**
     * -- 50m or about 5 * 10 ^ -4 decimal degrees
     */
    a50m(4),
    /**
     * -- 20m or about 2 * 10 ^ -4 decimal degrees
     */
    a20m(5),
    /**
     * -- 10m or about 1 * 10 ^ -4 decimal degrees
     */
    a10m(6),
    /**
     * -- 5m or about 5 * 10 ^ -5 decimal degrees
     */
    a5m(7),
    /**
     * -- 2m or about 2 * 10 ^ -5 decimal degrees
     */
    a2m(8),
    /**
     * -- 1m or about 1 * 10 ^ -5 decimal degrees
     */
    a1m(9),
    /**
     * -- 0.50m or about 5 * 10 ^ -6 decimal degrees
     */
    a50cm(10),
    /**
     * -- 0.20m or about 2 * 10 ^ -6 decimal degrees
     */
    a20cm(11),
    /**
     * -- 0.10m or about 1 * 10 ^ -6 decimal degrees
     */
    a10cm(12),
    /**
     * -- 0.05m or about 5 * 10 ^ -7 decimal degrees
     */
    a5cm(13),
    /**
     * -- 0.02m or about 2 * 10 ^ -7 decimal degrees
     */
    a2cm(14),
    /**
     * -- 0.01m or about 1 * 10 ^ -7 decimal degrees
     */
    a1cm(15);
    private final int value;

    PositionConfidence(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"value\":")
                .append(value);
        sb.append('}');
        return sb.toString();
    }
}
```

- ENUMERATED - restrictions or extension markers

```asn.1
ResponseType ::= ENUMERATED {
		notInUseOrNotEquipped (0),
		emergency (1), -- active service call at emergency level
		nonEmergency (2), -- also used when returning from service call
		pursuit (3), -- sender driving may be erratic
		stationary (4), -- sender is not moving, stopped along roadside
		slowMoving (5), -- such a litter trucks, etc.
		stopAndGoMovement (6), -- such as school bus or garbage truck
		...
	}
```

对应java代码：

```java

@HasExtensionMarker
public enum ResponseType {
    /**
     *
     */
    notInUseOrNotEquipped(0),
    /**
     * -- active service call at emergency level
     */
    emergency(1),
    /**
     * -- also used when returning from service call
     */
    nonEmergency(2),
    /**
     * -- sender driving may be erratic
     */
    pursuit(3),
    /**
     * -- sender is not moving, stopped along roadside
     */
    stationary(4),
    /**
     * -- such a litter trucks, etc.
     */
    slowMoving(5),
    /**
     * -- such as school bus or garbage truck
     */
    stopAndGoMovement(6),
    zoro(7);
    private final int value;

    ResponseType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(value);
        return builder.toString();
    }
}
```

- IA5String

```asn.1
DescriptiveName ::= IA5String (SIZE(1..63))
```

对应java代码：

```java

@RestrictedString(CharacterRestriction.IA5String)
@SizeRange(minValue = 1, maxValue = 63)
public class DescriptiveName extends Asn1String {
    public DescriptiveName() {
        this("");
    }

    public DescriptiveName(String value) {
        super(value);
    }
}
```

- OCTET STRING

```asn.1
id OCTET STRING (SIZE(8))
``` 

对应的Java代码：

```java

@SizeRange(minValue = 8, maxValue = 8)
public class ID extends Asn1SequenceOf<Byte> {
    // Two constructors -- choose any, or add more.
    public ID(Byte... coll) {
        this(Arrays.asList(coll));
    }

    public ID(Collection<Byte> coll) {
        super(coll);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\"");
        byte[] bytes = new byte[bakingList.size()];
        for (int i = 0; i < bakingList.size(); i++) {
            bytes[i] = bakingList.get(i);
        }
        sb.append(new String(bytes));
        sb.append("\"");
        return sb.toString();
    }
}
```

### Example 2:复合类型 restrictions or extension markers

- SEQUENCE:

```asn1
BSM DEFINITIONS AUTOMATIC TAGS ::= BEGIN

-- imports and exports

EXPORTS BasicSafetyMessage;
IMPORTS AccelerationSet4Way FROM DefAcceleration
		BrakeSystemStatus FROM VehBrake
		VehicleSize FROM VehSize
		Position3D, PositionConfidenceSet, PositionalAccuracy FROM DefPosition
		DSecond, TimeConfidence FROM DefTime
		TransmissionState FROM VehStatus
		Speed, Heading, SteeringWheelAngle, MotionConfidenceSet FROM DefMotion
		MsgCount FROM MsgFrame
		VehicleClassification FROM VehClass
		VehicleSafetyExtensions FROM VehSafetyExt
		VehicleEmergencyExtensions FROM VehEmgExt;
	
	BasicSafetyMessage ::= SEQUENCE {
		msgCnt MsgCount,
		id OCTET STRING (SIZE(8)),
		-- temperary vehicle ID
		secMark DSecond,
		timeConfidence TimeConfidence OPTIONAL,
		pos Position3D,
		posAccuracy PositionalAccuracy OPTIONAL,
		-- Accuracy for GNSS system
		posConfidence PositionConfidenceSet OPTIONAL,
		-- Realtime position confidence
		transmission TransmissionState,
		speed Speed,
		heading Heading,
		angle SteeringWheelAngle OPTIONAL,
		motionCfd MotionConfidenceSet OPTIONAL,
		accelSet AccelerationSet4Way,
		brakes BrakeSystemStatus,
		size VehicleSize,
		vehicleClass VehicleClassification,
		-- VehicleClassification includes BasicVehicleClass and other extendible type
		safetyExt VehicleSafetyExtensions OPTIONAL,
		emergencyExt VehicleEmergencyExtensions OPTIONAL,
		...
	}
	
END
```

对应的java代码：

```java

@Getter
@Setter
@Sequence
@HasExtensionMarker
public class BasicSafetyMessage implements AsnV2x {

    @Component(0)
    public MsgCount msgCnt;

    @Component(1)
    public ID id;

    @Component(2)
    public DSecond secMark;

    @Component(3)
    @Asn1Optional
    public TimeConfidence timeConfidence;

    @Component(4)
    public Position3D pos;

    @Component(5)
    @Asn1Optional
    public PositionalAccuracy posAccuracy;

    @Component(6)
    @Asn1Optional
    public PositionConfidenceSet posConfidence;

    @Component(7)
    public TransmissionState transmission;

    @Component(8)
    public Speed speed;

    @Component(9)
    public Heading heading;

    @Component(10)
    @Asn1Optional
    public SteeringWheelAngle angle;

    @Component(11)
    @Asn1Optional
    public MotionConfidenceSet motionCfd;

    @Component(12)
    public AccelerationSet4Way accelSet;

    @Component(13)
    public BrakeSystemStatus brakes;

    @Component(14)
    public VehicleSize size;

    @Component(15)
    public VehicleClassification vehicleClass;

    @Component(16)
    @Asn1Optional
    public VehicleSafetyExtensions safetyExt;

    @Component(17)
    @Asn1Optional
    public VehicleEmergencyExtensions emergencyExt;

    public BasicSafetyMessage() {
    }

    public BasicSafetyMessage(
            MsgCount msgCnt,
            ID id,
            DSecond secMark,
            TimeConfidence timeConfidence,
            Position3D pos,
            PositionalAccuracy posAccuracy,
            PositionConfidenceSet posConfidence,
            TransmissionState transmission,
            Speed speed,
            Heading heading,
            SteeringWheelAngle angle,
            MotionConfidenceSet motionCfd,
            AccelerationSet4Way accelSet,
            BrakeSystemStatus brakes,
            VehicleSize size,
            VehicleClassification vehicleClass,
            VehicleSafetyExtensions safetyExt,
            VehicleEmergencyExtensions emergencyExt
    ) {
        this.msgCnt = msgCnt;
        this.id = id;
        this.secMark = secMark;
        this.timeConfidence = timeConfidence;
        this.pos = pos;
        this.posAccuracy = posAccuracy;
        this.posConfidence = posConfidence;
        this.transmission = transmission;
        this.speed = speed;
        this.heading = heading;
        this.angle = angle;
        this.motionCfd = motionCfd;
        this.accelSet = accelSet;
        this.brakes = brakes;
        this.size = size;
        this.vehicleClass = vehicleClass;
        this.safetyExt = safetyExt;
        this.emergencyExt = emergencyExt;
    }
}
```

- CHOICE

```asn1
MessageFrame ::= CHOICE { 
		bsmFrame BasicSafetyMessage,
		mapFrame MapData,
		rsmFrame RoadsideSafetyMessage,
		spatFrame SPAT,
		rsiFrame RoadSideInformation,
		...
	}
```

对应的java代码：

```java

@Choice
@Getter
@HasExtensionMarker
public class MessageFrame {


    public BasicSafetyMessage bsmFrame;
    public MapData mapData;
    public RoadSideSafetyMessage rsmFrame;
    public SPAT spatFrame;
    public RoadSideInformation rsiFrame;

    public MessageFrame() {
    }

    public MessageFrame(BasicSafetyMessage bsmFrame,
                        MapData mapData,
                        RoadSideSafetyMessage rsmFrame,
                        SPAT spatFrame,
                        RoadSideInformation rsiFrame) {
        this.bsmFrame = bsmFrame;
        this.mapData = mapData;
        this.rsmFrame = rsmFrame;
        this.spatFrame = spatFrame;
        this.rsiFrame = rsiFrame;
    }

    public static MessageFrame bsmFrame(BasicSafetyMessage bsm) {
        return new MessageFrame(bsm, null, null, null, null);
    }

    public static MessageFrame mapFrame(MapData mapData) {
        return new MessageFrame(null, mapData, null, null, null);
    }

    public static MessageFrame rsmFrame(RoadSideSafetyMessage rsm) {
        return new MessageFrame(null, null, rsm, null, null);
    }

    public static MessageFrame spatFrame(SPAT spat) {
        return new MessageFrame(null, null, null, spat, null);
    }

    public static MessageFrame rsiFrame(RoadSideInformation rsi) {
        return new MessageFrame(null, null, null, null, rsi);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"bsmFrame\":")
                .append(bsmFrame);
        sb.append(",\"mapData\":")
                .append(mapData);
        sb.append(",\"rsmFrame\":")
                .append(rsmFrame);
        sb.append(",\"spatFrame\":")
                .append(spatFrame);
        sb.append(",\"rsiFrame\":")
                .append(rsiFrame);
        sb.append('}');
        return sb.toString();
    }

}
```

- SEQUENCE OF

```asn.1
IntersectionStateList ::= SEQUENCE (SIZE(1..32)) OF IntersectionState
``` 

对应java代码：

```java

@SizeRange(minValue = 1, maxValue = 32)
public static class IntersectionStateList extends Asn1SequenceOf<IntersectionState> {
    public IntersectionStateList(IntersectionState... intersectionStates) {
        this(Arrays.asList(intersectionStates));
    }

    public IntersectionStateList(Collection<IntersectionState> coll) {
        super(coll);
    }
}
```