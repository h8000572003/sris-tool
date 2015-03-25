/*
 * Copyright (c) 2010-2020 IISI.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of IISI.
 */
package com.iisi.tool;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class FileExportImpl implements FileExport {

    private transient Logger LOG = LoggerFactory.getLogger(FileExportImpl.class);

    public void export(String cotnet, String newName) {

        File f = new File(newName);

        FileWriter fw;
        try {

            fw = new FileWriter(newName);
            fw.write(cotnet);

            fw.flush();

            fw.close();
        } catch (IOException e) {
            LOG.error("error", e);
        }

        ;

    }
    // ================================================
    // == [Enumeration types] Block Start
    // == [Enumeration types] Block End
    // ================================================
    // == [static variables] Block Start
    // == [static variables] Block Stop
    // ================================================
    // == [instance variables] Block Start
    // == [instance variables] Block Stop
    // ================================================
    // == [static Constructor] Block Start
    // == [static Constructor] Block Stop
    // ================================================
    // == [Constructors] Block Start (含init method)
    // == [Constructors] Block Stop
    // ================================================
    // == [Static Method] Block Start
    // == [Static Method] Block Stop
    // ================================================
    // == [Accessor] Block Start
    // == [Accessor] Block Stop
    // ================================================
    // == [Overrided Method] Block Start (Ex. toString/equals+hashCode)
    // == [Overrided Method] Block Stop
    // ================================================
    // == [Method] Block Start
    // ####################################################################
    // ## [Method] sub-block :
    // ####################################################################
    // == [Method] Block Stop
    // ================================================
    // == [Inner Class] Block Start
    // == [Inner Class] Block Stop
    // ================================================
}
