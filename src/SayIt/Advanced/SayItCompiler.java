package SayIt.Advanced;

public class SayItCompiler
{
    public static class SayItCompiledData{
        public String currentClassName,extendedClassName;
        public String[] pool;
        public Number[] numpool;
        public int[] code;
        public SayItCompiledData(
            String currentClassName,
            String extendedClassName,
            String[] pool,
            Number[] numpool,
            int[] code
        ) {
            this.currentClassName = currentClassName;
            this.extendedClassName = extendedClassName;
            this.pool = pool;
            this.numpool = numpool;

            this.code = code;
            
        }
    }
}
