package com.yuanting.compiler;


import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleAnnotationValueVisitor7;

/**
 * Created on 2018/5/2 17:43
 * Created by xuelimin
 * TEL 13262933389
 */
public final class PayEntryVisitor extends SimpleAnnotationValueVisitor7<Void, Void> {
    private Filer mFiler = null;
    private TypeMirror mTypeMirror = null;
    private String mPackageName = null;

    public void setFiler(Filer mFiler) {
        this.mFiler = mFiler;
    }

    @Override
    public Void visitString(String s, Void p) {
        mPackageName = s;
        return p;
    }

    @Override
    public Void visitType(TypeMirror t, Void p) {
        mTypeMirror = t;
        generateJavaCode();
        return p;
    }

    private void generateJavaCode() {
        final TypeSpec targetActibity =
                TypeSpec.classBuilder("WXPayEntryActivity")
                        .addModifiers(Modifier.PUBLIC)
                        .addModifiers(Modifier.FINAL)
                        .superclass(TypeName.get(mTypeMirror))
                        .build();
        final JavaFile javaFile = JavaFile.builder(mPackageName+".wxapi",targetActibity)
                .addFileComment("WXPayEntry").build();
        try {
            javaFile.writeTo(mFiler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
