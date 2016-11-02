import org.objectweb.asm.*;


/**
 * Created by Yifan on 10/29/16.
 * if class name is the name, collect # of statements and pass it to adapter
 */
public class InformationCollecter extends ClassVisitor implements Opcodes {

    private final FirstPassInfo info;
    private final String className;

    public InformationCollecter(ClassVisitor cv, FirstPassInfo info, String name) {
        super(Opcodes.ASM5, cv);
        this.info = info;
        this.className = name;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        return new Counter(mv, info, name, desc);
    }

    private class Counter extends MethodVisitor implements Opcodes {
        private final FirstPassInfo myInfo;
        private final String methodName;
        private final String methodDesc;

        public Counter(MethodVisitor mv, FirstPassInfo myInfo, String methodName, String methodDesc) {
            super(ASM5, mv);
            this.myInfo = myInfo;
            this.methodName = methodName;
            this.methodDesc = methodDesc;
        }

        @Override
        public void visitLineNumber(int line, Label start) {
//            System.out.println(methodName+methodDesc+line);
            myInfo.saveMethodInfo(methodName, methodDesc, line);
            mv.visitLineNumber(line, start);
        }
    }
}
