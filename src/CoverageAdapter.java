import org.objectweb.asm.*;

/**
 * todo 再详细设计一下runtime纪录过程
 * redirect to file?
 * use a static field to track?
 * Created by Yifan on 10/29/16.
 */
public class CoverageAdapter extends ClassVisitor implements Opcodes {

    private boolean needAdapt;

    public CoverageAdapter(ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        if (mv != null && needAdapt){
            mv = new Adapter(mv);
        }
        return mv;
    }

    private class Adapter extends MethodVisitor implements Opcodes{
        public Adapter(MethodVisitor mv){
            super(ASM5, mv);
        }

        @Override
        public void visitLineNumber(int line, Label start) {
            //do something: 在每个line后面插入一个probe
            mv.visitLineNumber(line, start);
        }
    }
}
