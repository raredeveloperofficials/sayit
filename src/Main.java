import SayIt.SIBuiltins;
import SayIt.SIRunner;
import SayIt.SyntaxReaders.ExpressionResult;
import SayIt.basic.SIExpression;
import SayIt.basic.SIObject;
import SayIt.basic.SIString;
import SayIt.main.Compiled;
import SayIt.main.Compiler;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import SayIt.basic.SIOverloadFunction;
public class Main {


    private static String version = "version:alpha 0.2v \n[single dev version]";
    public static void main(String[] args){
        main2(new String[]{"version"});
        main2(new String[]{"compile","/storage/emulated/0/SayIt/main.si","/storage/emulated/0/SayIt/main.sic"});
        main2(new String[]{"run","/storage/emulated/0/SayIt/main.sic"});
    }
    public static void main2(String[] args) {
        if (args.length == 0) {
            printUsage();
            return;
        }

        String command = args[0];

        if (command.equals("compile")) {
            if (args.length != 3) {
                System.err.println(
                    "Error: compile requires source and output paths."
                );
                printUsage();
                return;
            }

            File sourceFile = resolvePath(args[1]);
            File outputFile = resolvePath(args[2]);

            if (!sourceFile.isFile()) {
                System.err.println(
                    "Source file not found: " + sourceFile
                );
                return;
            }
            String source = "";
            try {
                source = readFile(sourceFile.getAbsolutePath());
            } catch (Exception e) {}

            if (source == null) {
                System.err.println("Failed to read source file.");
                return;
            }

            System.out.println("Source:\n" + source);
            System.out.println("\nCompiling...");

            

            Compiled compiled;
            try {
                compiled = Compiler.compile(source.toCharArray());

                if (compiled == null) {
                    System.err.println("Compilation returned null.");
                    return;
                }

                File parent = outputFile.getParentFile();
                if (parent != null && !parent.exists()
                    && !parent.mkdirs()) {
                    System.err.println(
                        "Cannot create output directory: " + parent
                    );
                    return;
                }

                compiled.writeToFile(outputFile.getAbsolutePath());

                System.out.println(
                    "Compiled successfully: "
                    + outputFile.getAbsolutePath()
                );

            } catch (IOException e) {
                System.err.println(
                    "Failed to write compiled file: " + e.getMessage()
                );
            }

        } else if (command.equals("run")) {
            SIBuiltins.init();
            if (args.length != 2) {
                System.err.println(
                    "Error: run requires one compiled-file path."
                );
                printUsage();
                return;
            }

            File compiledFile = resolvePath(args[1]);

            if (!compiledFile.isFile()) {
                System.err.println(
                    "Compiled file not found: " + compiledFile
                );
                return;
            }

            Compiled compiled;
            try {
                compiled = Compiled.readFromFile(
                    compiledFile.getAbsolutePath()
                );
            } catch (IOException e) {
                System.err.println(
                    "Failed to load compiled file: " + e.getMessage()
                );
                return;
            }

            if (compiled == null) {
                System.err.println("Compiled file contains no program.");
                return;
            }

            SIBuiltins.init();

            SIString titan = new SIString();
            titan.set("titan11200");

            int titanindex = SIRunner.addVariable("string", titan);

            long start = System.nanoTime();

            SIRunner.run(new HashMap<String,SIOverloadFunction>(),
                compiled,
                new String[0],
                new SIObject[0],
                new String[]{"titan"},
                new Integer[]{titanindex});

            double delay =
                (System.nanoTime() - start) / 1_000_000.0;

            System.out.println("\nruntime: " + delay + " ms");

        }else if(command.equals("version")){
            System.out.println(version);
        } else {
            System.err.println("Unknown command: " + command);
            printUsage();
        }
    }

    private static File resolvePath(String path) {
        // Absolute paths stay absolute; relative paths use the current directory.
        return new File(path).getAbsoluteFile();
    }

    private static void printUsage() {
        System.out.println(
            "Say It Compiler\n\n" +
            "Usage:\n" +
            "  java Main compile <source.si> <output.sic>\n" +
            "  java Main run <file.sic>\n\n" +
            "Examples:\n" +
            "  java Main compile main.si main.sic\n" +
            "  java Main compile /storage/emulated/0/SayIt/main.si " +
            "/storage/emulated/0/SayIt/main.sic\n" +
            "  java Main run /storage/emulated/0/SayIt/main.sic"
        );
    }
    

    public static String readFile(String filename) throws Exception{
        StringBuilder content = new StringBuilder();

        try {
            try (BufferedReader reader =
                new BufferedReader(new FileReader(filename))) {

                String line;

                try {
                    while ((line = reader.readLine()) != null) {
                        content.append(line).append("\n");
                    }
                } catch (IOException e) {}

            }
        } catch (FileNotFoundException e) {} 

        return content.toString();
    }
    
    public static void read(ExpressionResult expr) {
        SIObject[] s =expr.expressions;
        Integer[] in = expr.do_operation;
        System.out.println("{");
        for (int i=0;i < s.length;i++) {

            SIObject o = s[i];
            if (o instanceof SIExpression) {
                read(((SIExpression)o).get());
            } else {
                System.out.println(o.get());
            }
            if (in.length > i) {
                System.out.println("op:" + in[i]);
            }
        }
        System.out.println("}");
    }

    public static void printVmBinary() {
        System.out.println("=== VM Binary ===");

        System.out.println("DECLARE: " + SIRunner.DECLARE);
        System.out.println("TYPE: " + SIRunner.TYPE);
        System.out.println("ASSIGN: " + SIRunner.ASSIGN);
        System.out.println("VAR: " + SIRunner.VAR);
        System.out.println("EXPRESSION: " + SIRunner.EXPRESSION);
        System.out.println("VALUE: " + SIRunner.VALUE);
        System.out.println("POOL: " + SIRunner.POOL);
        System.out.println("CALL: " + SIRunner.CALL);
        System.out.println("BYCALL: " + SIRunner.BYCALL);
        System.out.println("OBJECT: " + SIRunner.OBJECT);
        System.out.println("ADDRESS: " + SIRunner.ADDRESS);
        System.out.println("ARGS_START: " + SIRunner.ARGS_START);
        System.out.println("ARGS_END: " + SIRunner.ARGS_END);
        System.out.println("ADD: " + SIRunner.ADD);
        System.out.println("SUB: " + SIRunner.SUB);
        System.out.println("MUL: " + SIRunner.MUL);
        System.out.println("DIV: " + SIRunner.DIV);
        System.out.println("MOD: " + SIRunner.MOD);
        System.out.println("GREATER: " + SIRunner.GREATER);
        System.out.println("LESS: " + SIRunner.LESS);
        System.out.println("GREATER_OR_EQUAL: " + SIRunner.GREATER_OR_EQUAL);
        System.out.println("LESS_OR_EQUAL: " + SIRunner.LESS_OR_EQUAL);
        System.out.println("EQUAL: " + SIRunner.EQUAL);
        System.out.println("ADD_EQUAL: " + SIRunner.ADD_EQUAL);
        System.out.println("SUB_EQUAL: " + SIRunner.SUB_EQUAL);
        System.out.println("MUL_EQUAL: " + SIRunner.MUL_EQUAL);
        System.out.println("DIV_EQUAL: " + SIRunner.DIV_EQUAL);
        System.out.println("MOD_EQUAL: " + SIRunner.MOD_EQUAL);
        System.out.println("NOT_EQUAL: " + SIRunner.NOT_EQUAL);
    }

}
