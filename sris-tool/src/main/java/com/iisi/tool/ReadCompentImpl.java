/*
 * Copyright (c) 2010-2020 IISI.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of IISI.
 */
package com.iisi.tool;

import java.io.BufferedReader;
import java.io.FileReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 */

@Component
public class ReadCompentImpl implements ReadCompent {

    private transient Logger LOG = LoggerFactory.getLogger(ReadCompentImpl.class);

    public String getContentBy(String Path) {

        StringBuffer bur = new StringBuffer();
        FileReader fr = null;
        try {

            fr = new FileReader(Path);

            BufferedReader br = new BufferedReader(fr);

            while (br.ready()) {
                bur.append(br.readLine() + "\n");
            }
            fr.close();
        } catch (Exception e) {
            LOG.error("e:{}", e);
        } finally {

        }

        // TODO Auto-generated method stub
        return bur.toString();
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
