/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package examples.dao.tiger;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.seasar.dao.tiger.FetchHandler;

/**
 * @author jundu
 * 
 */
public class EmployeeFileWriter implements FetchHandler<Employee> {

    private boolean initialized = false;

    private String filePath;

    private FileOutputStream fileOutputStream;

    private PrintWriter printWriter;

    private int rowCount = 0;

    public EmployeeFileWriter(String filePath) {
        this.filePath = filePath;
    }

    public void initialize() throws FileNotFoundException {
        if (initialized) {
            return;
        }
        fileOutputStream = new FileOutputStream(filePath);
        printWriter = new PrintWriter(fileOutputStream);
        initialized = true;
    }

    public void close() throws IOException {
        if (!initialized) {
            return;
        }
        printWriter.close();
        fileOutputStream.close();
        initialized = false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.seasar.dao.tiger.FetchHandler#execute(java.lang.Object)
     */
    public boolean execute(Employee emp) {
        if (!initialized) {
            throw new IllegalStateException();
        }
        Department dept = emp.getDepartment();
        StringBuilder sb = new StringBuilder(255);
        sb.append(emp.getEmpno()).append(",");
        sb.append(emp.getEname()).append(",");
        sb.append(emp.getJob()).append(",");
        sb.append(emp.getMgr()).append(",");
        sb.append(emp.getHiredate()).append(",");
        sb.append(emp.getSal()).append(",");
        sb.append(emp.getComm()).append(",");
        sb.append(dept.getDeptno()).append(",");
        sb.append(dept.getDname()).append(",");
        sb.append(dept.getLoc());

        // CSVファイルに書き出す。
        printWriter.println(sb.toString());

        rowCount++;
        if (rowCount >= 100) {
            // 最大100件出力したら、そこでやめる。
            return false;
        }
        return true;
    }

}
