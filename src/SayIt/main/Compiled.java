package SayIt.main;
import SayIt.basic.SIFunction;


import java.io.*;

public class Compiled {
    public String[] pool;
    public Number[] numpool;
    public Integer[] code;
    public Integer[][] expressions;
    public Compiled[] block;

    public Compiled(String[] pool, Number[] numpool,
                    Integer[] code, Integer[][] expressions,
                    Compiled[] block) {
        this.pool = pool;
        this.numpool = numpool;
        this.expressions = expressions;
        this.code = code;
        this.block = block;
    }

    // Save Compiled to a file
    public void writeToFile(String path) throws IOException {
        DataOutputStream out = new DataOutputStream(
            new BufferedOutputStream(
                new FileOutputStream(path)
            )
        );

        try {
            writeCompiled(out, this);
        } finally {
            out.close();
        }
    }

    private static void writeCompiled(
        DataOutputStream out, Compiled c
    ) throws IOException {

        if (c == null) {
            out.writeBoolean(false);
            return;
        }

        out.writeBoolean(true);

        // String pool
        out.writeInt(c.pool.length);
        for (String s : c.pool) {
            out.writeBoolean(s != null);
            if (s != null) out.writeUTF(s);
        }

        // Number pool
        out.writeInt(c.numpool.length);
        for (Number n : c.numpool) {
            if (n instanceof Integer) {
                out.writeByte(1);
                out.writeInt(n.intValue());
            } else if (n instanceof Long) {
                out.writeByte(2);
                out.writeLong(n.longValue());
            } else if (n instanceof Float) {
                out.writeByte(3);
                out.writeFloat(n.floatValue());
            } else if (n instanceof Double) {
                out.writeByte(4);
                out.writeDouble(n.doubleValue());
            } else if (n == null) {
                out.writeByte(0);
            } else {
                throw new IOException(
                    "Unsupported Number type: "
                    + n.getClass().getName()
                );
            }
        }

        // Code
        out.writeInt(c.code.length);
        for (Integer i : c.code) {
            out.writeBoolean(i != null);
            if (i != null) out.writeInt(i);
        }

        // Expressions
        out.writeInt(c.expressions.length);
        for (Integer[] exp : c.expressions) {
            out.writeBoolean(exp != null);

            if (exp != null) {
                out.writeInt(exp.length);
                for (Integer i : exp) {
                    out.writeBoolean(i != null);
                    if (i != null) out.writeInt(i);
                }
            }
        }

        // Nested blocks
        out.writeInt(c.block.length);
        for (Compiled b : c.block) {
            writeCompiled(out, b);
        }
    }

    // Read Compiled from a file
    public static Compiled readFromFile(String path)
    throws IOException {

        DataInputStream in = new DataInputStream(
            new BufferedInputStream(
                new FileInputStream(path)
            )
        );

        try {
            return readCompiled(in);
        } finally {
            in.close();
        }
    }

    private static Compiled readCompiled(
        DataInputStream in
    ) throws IOException {

        if (!in.readBoolean()) return null;

        // String pool
        int len = in.readInt();
        String[] pool = new String[len];

        for (int i = 0; i < len; i++) {
            if (in.readBoolean()) {
                pool[i] = in.readUTF();
            }
        }

        // Number pool
        len = in.readInt();
        Number[] numpool = new Number[len];

        for (int i = 0; i < len; i++) {
            switch (in.readByte()) {
                case 0:
                    numpool[i] = null;
                    break;
                case 1:
                    numpool[i] = in.readInt();
                    break;
                case 2:
                    numpool[i] = in.readLong();
                    break;
                case 3:
                    numpool[i] = in.readFloat();
                    break;
                case 4:
                    numpool[i] = in.readDouble();
                    break;
                default:
                    throw new IOException(
                        "Unknown Number type"
                    );
            }
        }

        // Code
        len = in.readInt();
        Integer[] code = new Integer[len];

        for (int i = 0; i < len; i++) {
            if (in.readBoolean()) {
                code[i] = in.readInt();
            }
        }

        // Expressions
        len = in.readInt();
        Integer[][] expressions = new Integer[len][];

        for (int i = 0; i < len; i++) {
            if (in.readBoolean()) {
                int size = in.readInt();
                expressions[i] = new Integer[size];

                for (int j = 0; j < size; j++) {
                    if (in.readBoolean()) {
                        expressions[i][j] = in.readInt();
                    }
                }
            }
        }

        // Nested blocks
        len = in.readInt();
        Compiled[] block = new Compiled[len];

        for (int i = 0; i < len; i++) {
            block[i] = readCompiled(in);
        }

        return new Compiled(
            pool, numpool, code, expressions, block
        );
    }
}

