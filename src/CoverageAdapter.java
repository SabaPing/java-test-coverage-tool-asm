import org.objectweb.asm.*;

/**
 * todo 再详细设计一下runtime纪录过程
 * redirect to file?
 * use a static field to track?
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

    @Override
    public void visitEnd() {
        inject(cv);
        cv.visitEnd();
    }

    /**
     * helper method -- help inject a static method to target class
     */
    private void inject(ClassVisitor cv){
        MethodVisitor mv = cv.visitMethod(ACC_PRIVATE + ACC_STATIC, "collect", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;I)V", null, null);
        mv.visitCode();
        Label l0 = new Label();
        Label l1 = new Label();
        Label l2 = new Label();
        mv.visitTryCatchBlock(l0, l1, l2, "java/lang/Throwable");
        Label l3 = new Label();
        Label l4 = new Label();
        Label l5 = new Label();
        mv.visitTryCatchBlock(l3, l4, l5, "java/lang/Throwable");
        Label l6 = new Label();
        mv.visitTryCatchBlock(l3, l4, l6, null);
        Label l7 = new Label();
        Label l8 = new Label();
        Label l9 = new Label();
        mv.visitTryCatchBlock(l7, l8, l9, "java/lang/Throwable");
        Label l10 = new Label();
        mv.visitTryCatchBlock(l5, l10, l6, null);
        Label l11 = new Label();
        Label l12 = new Label();
        Label l13 = new Label();
        mv.visitTryCatchBlock(l11, l12, l13, "java/io/IOException");
        Label l14 = new Label();
        mv.visitLabel(l14);
        mv.visitLineNumber(17, l14);
        mv.visitLdcInsn("raw_result");
        mv.visitInsn(ICONST_0);
        mv.visitTypeInsn(ANEWARRAY, "java/lang/String");
        mv.visitMethodInsn(INVOKESTATIC, "java/nio/file/Paths", "get", "(Ljava/lang/String;[Ljava/lang/String;)Ljava/nio/file/Path;", false);
        mv.visitVarInsn(ASTORE, 4);
        mv.visitLabel(l11);
        mv.visitLineNumber(18, l11);
        mv.visitTypeInsn(NEW, "java/io/PrintWriter");
        mv.visitInsn(DUP);
        mv.visitVarInsn(ALOAD, 4);
        mv.visitInsn(ICONST_2);
        mv.visitTypeInsn(ANEWARRAY, "java/nio/file/OpenOption");
        mv.visitInsn(DUP);
        mv.visitInsn(ICONST_0);
        mv.visitFieldInsn(GETSTATIC, "java/nio/file/StandardOpenOption", "CREATE", "Ljava/nio/file/StandardOpenOption;");
        mv.visitInsn(AASTORE);
        mv.visitInsn(DUP);
        mv.visitInsn(ICONST_1);
        mv.visitFieldInsn(GETSTATIC, "java/nio/file/StandardOpenOption", "APPEND", "Ljava/nio/file/StandardOpenOption;");
        mv.visitInsn(AASTORE);
        Label l15 = new Label();
        mv.visitLabel(l15);
        mv.visitLineNumber(19, l15);
        mv.visitMethodInsn(INVOKESTATIC, "java/nio/file/Files", "newBufferedWriter", "(Ljava/nio/file/Path;[Ljava/nio/file/OpenOption;)Ljava/io/BufferedWriter;", false);
        mv.visitMethodInsn(INVOKESPECIAL, "java/io/PrintWriter", "<init>", "(Ljava/io/Writer;)V", false);
        mv.visitVarInsn(ASTORE, 5);
        Label l16 = new Label();
        mv.visitLabel(l16);
        mv.visitLineNumber(18, l16);
        mv.visitInsn(ACONST_NULL);
        mv.visitVarInsn(ASTORE, 6);
        mv.visitLabel(l3);
        mv.visitLineNumber(21, l3);
        mv.visitVarInsn(ALOAD, 5);
        mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
        mv.visitLdcInsn(" ");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
        mv.visitLdcInsn(" ");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
        mv.visitVarInsn(ALOAD, 2);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
        mv.visitLdcInsn(": ");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
        mv.visitVarInsn(ILOAD, 3);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(I)Ljava/lang/StringBuilder;", false);
        mv.visitLdcInsn(" has been visited.");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintWriter", "println", "(Ljava/lang/String;)V", false);
        mv.visitLabel(l4);
        mv.visitLineNumber(22, l4);
        mv.visitVarInsn(ALOAD, 5);
        mv.visitJumpInsn(IFNULL, l12);
        mv.visitVarInsn(ALOAD, 6);
        Label l17 = new Label();
        mv.visitJumpInsn(IFNULL, l17);
        mv.visitLabel(l0);
        mv.visitVarInsn(ALOAD, 5);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintWriter", "close", "()V", false);
        mv.visitLabel(l1);
        mv.visitJumpInsn(GOTO, l12);
        mv.visitLabel(l2);
        mv.visitFrame(Opcodes.F_FULL, 7, new Object[]{"java/lang/String", "java/lang/String", "java/lang/String", Opcodes.INTEGER, "java/nio/file/Path", "java/io/PrintWriter", "java/lang/Throwable"}, 1, new Object[]{"java/lang/Throwable"});
        mv.visitVarInsn(ASTORE, 7);
        mv.visitVarInsn(ALOAD, 6);
        mv.visitVarInsn(ALOAD, 7);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Throwable", "addSuppressed", "(Ljava/lang/Throwable;)V", false);
        mv.visitJumpInsn(GOTO, l12);
        mv.visitLabel(l17);
        mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
        mv.visitVarInsn(ALOAD, 5);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintWriter", "close", "()V", false);
        mv.visitJumpInsn(GOTO, l12);
        mv.visitLabel(l5);
        mv.visitLineNumber(18, l5);
        mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[]{"java/lang/Throwable"});
        mv.visitVarInsn(ASTORE, 7);
        mv.visitVarInsn(ALOAD, 7);
        mv.visitVarInsn(ASTORE, 6);
        mv.visitVarInsn(ALOAD, 7);
        mv.visitInsn(ATHROW);
        mv.visitLabel(l6);
        mv.visitLineNumber(22, l6);
        mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[]{"java/lang/Throwable"});
        mv.visitVarInsn(ASTORE, 8);
        mv.visitLabel(l10);
        mv.visitVarInsn(ALOAD, 5);
        Label l18 = new Label();
        mv.visitJumpInsn(IFNULL, l18);
        mv.visitVarInsn(ALOAD, 6);
        Label l19 = new Label();
        mv.visitJumpInsn(IFNULL, l19);
        mv.visitLabel(l7);
        mv.visitVarInsn(ALOAD, 5);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintWriter", "close", "()V", false);
        mv.visitLabel(l8);
        mv.visitJumpInsn(GOTO, l18);
        mv.visitLabel(l9);
        mv.visitFrame(Opcodes.F_FULL, 9, new Object[]{"java/lang/String", "java/lang/String", "java/lang/String", Opcodes.INTEGER, "java/nio/file/Path", "java/io/PrintWriter", "java/lang/Throwable", Opcodes.TOP, "java/lang/Throwable"}, 1, new Object[]{"java/lang/Throwable"});
        mv.visitVarInsn(ASTORE, 9);
        mv.visitVarInsn(ALOAD, 6);
        mv.visitVarInsn(ALOAD, 9);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Throwable", "addSuppressed", "(Ljava/lang/Throwable;)V", false);
        mv.visitJumpInsn(GOTO, l18);
        mv.visitLabel(l19);
        mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
        mv.visitVarInsn(ALOAD, 5);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintWriter", "close", "()V", false);
        mv.visitLabel(l18);
        mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
        mv.visitVarInsn(ALOAD, 8);
        mv.visitInsn(ATHROW);
        mv.visitLabel(l12);
        mv.visitLineNumber(25, l12);
        mv.visitFrame(Opcodes.F_FULL, 5, new Object[]{"java/lang/String", "java/lang/String", "java/lang/String", Opcodes.INTEGER, "java/nio/file/Path"}, 0, new Object[]{});
        Label l20 = new Label();
        mv.visitJumpInsn(GOTO, l20);
        mv.visitLabel(l13);
        mv.visitLineNumber(22, l13);
        mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[]{"java/io/IOException"});
        mv.visitVarInsn(ASTORE, 5);
        Label l21 = new Label();
        mv.visitLabel(l21);
        mv.visitLineNumber(23, l21);
        mv.visitVarInsn(ALOAD, 5);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/IOException", "printStackTrace", "()V", false);
        Label l22 = new Label();
        mv.visitLabel(l22);
        mv.visitLineNumber(24, l22);
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "err", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("file error");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        mv.visitLabel(l20);
        mv.visitLineNumber(26, l20);
        mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
        mv.visitInsn(RETURN);
        Label l23 = new Label();
        mv.visitLabel(l23);
        mv.visitLocalVariable("out", "Ljava/io/PrintWriter;", null, l16, l12, 5);
        mv.visitLocalVariable("e", "Ljava/io/IOException;", null, l21, l20, 5);
        mv.visitLocalVariable("className", "Ljava/lang/String;", null, l14, l23, 0);
        mv.visitLocalVariable("methodName", "Ljava/lang/String;", null, l14, l23, 1);
        mv.visitLocalVariable("desc", "Ljava/lang/String;", null, l14, l23, 2);
        mv.visitLocalVariable("line", "I", null, l14, l23, 3);
        mv.visitLocalVariable("path", "Ljava/nio/file/Path;", null, l11, l23, 4);
        mv.visitMaxs(7, 10);
        mv.visitEnd();
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
            mv.visitLdcInsn(new Integer(line));
            mv.visitMethodInsn(INVOKESTATIC, className, "collect", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;I)V", false);
            super.visitLineNumber(line, start);
        }
    }
}
