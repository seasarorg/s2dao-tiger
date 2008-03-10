/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
import java.util.List;
import java.util.Map;

import org.seasar.dao.tiger.FetchHandler;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.S2ContainerFactory;

public class EmployeeDaoClient {

    private static final String PATH = "examples/dao/tiger/EmployeeDao.dicon";

    public static void main(String[] args) {
        S2Container container = S2ContainerFactory.create(PATH);
        container.init();
        try {
            EmployeeDao dao = (EmployeeDao) container
                    .getComponent(EmployeeDao.class);
            List<?> employees = dao.getAllEmployees();
            for (int i = 0; i < employees.size(); ++i) {
                System.out.println(employees.get(i));
            }

            List<Map<String, Object>> valueLabels = dao.getValueLabel();
            for (Map<String, Object> row : valueLabels) {
                System.out.println(row);
            }

            List<Integer> numbers = dao.getAllEmployeeNumbers();
            for (Integer number : numbers) {
                System.out.println(number);
            }

            Employee employee = dao.getEmployee(7788);
            System.out.println(employee);

            int count = dao.getCount();
            System.out.println("count:" + count);

            dao.getEmployeeByJobDeptno(null, null);
            dao.getEmployeeByJobDeptno("CLERK", null);
            dao.getEmployeeByJobDeptno(null, new Integer(20));
            dao.getEmployeeByJobDeptno("CLERK", new Integer(20));

            System.out.println("updatedRows:" + dao.update(employee));

            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream("example.csv");
                final PrintWriter writer = new PrintWriter(fos);
                dao.fetchAllEmployee(new FetchHandler<Employee>() {
                    public boolean execute(Employee emp) {
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
                        writer.println(sb.toString());
                        return true;
                    }
                });
                writer.flush();
                writer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } finally {
            container.destroy();
        }

    }
}