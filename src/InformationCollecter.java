import org.objectweb.asm.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Yifan on 10/29/16.
 * if class name is the name, collect # of statements and pass it to adapter
 */
public class InformationCollecter extends ClassVisitor implements Opcodes{

    //    private CoverageDriver driver;
    private boolean needAdapt;
    private int statementsCounter;
    private String name;

    private String projectName;

    public InformationCollecter(ClassVisitor cv, String pName) {
        super(Opcodes.ASM5, cv);
//        this.driver = d;
        statementsCounter = 0;
        projectName = pName;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        //一般test都放在test package下，并且jvm class name包括package路径，thus，
        if (name.contains(projectName) && !name.contains("/test/")){
            this.needAdapt = true;
        } else {
            this.needAdapt = false;
        }
        this.name = name;
        cv.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        if (mv != null && needAdapt){
            mv = new Counter(mv);
        }
        return mv;
    }

    private class Counter extends MethodVisitor implements Opcodes{
        public Counter(MethodVisitor mv){
            super(ASM5, mv);
        }
        @Override
        public void visitLineNumber(int line, Label start) {
            InformationCollecter.this.statementsCounter++;
            mv.visitLineNumber(line, start);
        }
    }

    int getStatementsCounter(){
        return statementsCounter;
    }
    boolean getNeedAdapt() {
        return this.needAdapt;
    }
    String getName() {
        return name;
    }
}
