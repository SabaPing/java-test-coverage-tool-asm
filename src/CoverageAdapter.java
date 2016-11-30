import org.objectweb.asm.*;

/**
 * Created by Yifan on 10/29/16.
 */
public class CoverageAdapter extends ClassVisitor implements Opcodes {

    private final String className;

    public CoverageAdapter(ClassVisitor cv, String className) {
        super(Opcodes.ASM5, cv);
        this.className = className;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        return new Injecter(mv, name, desc);
    }

    private class Injecter extends MethodVisitor implements Opcodes {
        private final String methodName;
        private final String methodDesc;

        public Injecter(MethodVisitor mv, String name, String desc) {
            super(ASM5, mv);
            this.methodName = name;
            this.methodDesc = desc;
        }

        @Override
        public void visitLineNumber(int line, Label start) {
            mv.visitLdcInsn(className);
            mv.visitLdcInsn(methodName);
            mv.visitLdcInsn(methodDesc);
            mv.visitIntInsn(SIPUSH, line);
            mv.visitMethodInsn(INVOKESTATIC, "CoverageDriver", "collect", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;I)V", false);
//            mv.visitMaxs(0,0);
//            mv.visitEnd();
            super.visitLineNumber(line, start);
        }
    }
}
