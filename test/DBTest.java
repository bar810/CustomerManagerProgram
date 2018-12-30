/**
 * Copyright 2013 Mentor Graphics Corporation All Rights Reserved
 * THIS WORK CONTAINS TRADE SECRET AND PROPRIETARY INFORMATION WHICH IS THE PROPERTY OF MENTOR GRAPHICS
 * CORPORATION OR ITS LICENSORS AND IS SUBJECT TO LICENSE TERMS.
 */

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.GlobalCommands;

import static utils.SQLQueries.GeneralScripts.removeAllDataFromAllDBTables;

/**
 * @author bbrownsh
 * @since 12/30/2018
 */
public class DBTest {

    @Before
    public void init() {
        GlobalCommands.init();
    }

    @Test
    public void CleanDB() {
        removeAllDataFromAllDBTables();
    }

    @After
    public void finish() {
        GlobalCommands.closeConnections();
    }
}
