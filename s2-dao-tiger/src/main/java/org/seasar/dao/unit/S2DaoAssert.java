/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
package org.seasar.dao.unit;

import java.util.List;
import java.util.Map;

import org.seasar.dao.BeanMetaData;
import org.seasar.dao.BeanMetaDataFactory;
import org.seasar.extension.dataset.DataSet;
import org.seasar.extension.dataset.DataTable;
import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;

/**
 * S2Dao用のアサートメソッドの集合です。
 * <p>
 * このクラスに定義されたアサートメソッドはJUnit4のテストメソッドから呼び出されることを想定しています。
 * いくつかのメソッドは、特定のコンポーネントがS2コンテナに登録されていることを前提に動作します。
 * </p>
 * <p>
 * static importを宣言して利用してください。
 * 
 * <pre>
 * import org.seasar.dao.unit.S2DaoAssert.*;
 *   ...
 *   assertEquals(...);
 * </pre>
 * 
 * </p>
 * 
 * @author taedium
 */
public class S2DaoAssert {

    /** {@link S2TestCase}のアダプタ */
    protected static final S2TestCaseAdapter adapter = new S2TestCaseAdapter();

    /**
     * インスタンスを構築します。
     * 
     */
    protected S2DaoAssert() {
    }

    /**
     * 2つのデータセットが等しいことをアサートします。
     * 
     * @param expected
     *            期待値
     * @param actual
     *            実際値
     */
    public static void assertEquals(DataSet expected, DataSet actual) {
        adapter.assertEquals(null, expected, actual);
    }

    /**
     * 2つのデータセットが等しいことをアサートします。もし等しくない場合、エラー情報に指定したメッセージが含まれます。
     * 
     * @param message
     *            メッセージ
     * @param expected
     *            期待値
     * @param actual
     *            実際値
     */
    public static void assertEquals(String message, DataSet expected,
            DataSet actual) {
        adapter.assertEquals(message, expected, actual);
    }

    /**
     * 2つのデータテーブルが等しいことをアサートします。
     * 
     * @param expected
     *            期待値
     * @param actual
     *            実際値
     */
    public static void assertEquals(DataTable expected, DataTable actual) {
        adapter.assertEquals(null, expected, actual);
    }

    /**
     * 2つのデータテーブルが等しいことをアサートします。もし等しくない場合、エラー情報に指定したメッセージが含まれます。
     * 
     * @param message
     *            メッセージ
     * @param expected
     *            期待値
     * @param actual
     *            実際値
     */
    public static void assertEquals(String message, DataTable expected,
            DataTable actual) {
        adapter.assertEquals(message, expected, actual);
    }

    /**
     * データセットとオブジェクトが等しいことをアサートします。
     * 
     * @param expected
     *            期待値
     * @param actual
     *            実際値
     */
    public static void assertEquals(DataSet expected, Object actual) {
        adapter.assertEquals(null, expected, actual);
    }

    /**
     * データセットとオブジェクトが等しいことをアサートします。 もし等しくない場合、エラー情報に指定したメッセージが含まれます。
     * 
     * @param message
     *            メッセージ
     * @param expected
     *            期待値
     * @param actual
     *            実際値
     */
    public static void assertEquals(String message, DataSet expected,
            Object actual) {
        adapter.assertEquals(message, expected, actual);
    }

    /**
     * データセットとマップが等しいことをアサートします。
     * 
     * @param <V>
     *            マップの値の型
     * @param expected
     *            期待値
     * @param map
     *            実際値
     */
    public static <V> void assertMapEquals(DataSet expected, Map<String, V> map) {
        adapter.assertMapEquals(null, expected, map);
    }

    /**
     * データセットとマップが等しいことをアサートします。もし等しくない場合、エラー情報に指定したメッセージが含まれます。
     * 
     * @param <V>
     *            マップの値の型
     * @param message
     *            メッセージ
     * @param expected
     *            期待値
     * @param map
     *            実際値
     */
    public static <V> void assertMapEquals(String message, DataSet expected,
            Map<String, V> map) {
        adapter.assertMapEquals(message, expected, map);
    }

    /**
     * データセットとマップのリストが等しいことをアサートします。
     * 
     * @param <V>
     *            マップの値の型
     * @param expected
     *            期待値
     * @param list
     *            実際値
     */
    public static <V> void assertMapEquals(DataSet expected,
            List<Map<String, V>> list) {
        adapter.assertMapListEquals(null, expected, list);
    }

    /**
     * データセットとマップのリストが等しいことをアサートします。もし等しくない場合、エラー情報に指定したメッセージが含まれます。
     * 
     * @param <V>
     *            マップの値の型
     * @param message
     *            メッセージ
     * @param expected
     *            期待値
     * @param list
     *            実際値
     */
    public static <V> void assertMapEquals(String message, DataSet expected,
            List<Map<String, V>> list) {
        adapter.assertMapListEquals(message, expected, list);
    }

    /**
     * データセットとビーンが等しいことをアサートします。
     * <p>
     * このメソッドを呼び出す前に、{@link BeanMetaDataFactory}のコンポーネントがS2コンテナに登録されている必要があります。
     * </p>
     * 
     * @param expected
     *            期待値
     * @param bean
     *            実際値
     */
    public static void assertBeanEquals(DataSet expected, Object bean) {
        assertBeanEquals(null, expected, bean);
    }

    /**
     * データセットとビーンが等しいことをアサートします。もし等しくない場合、エラー情報に指定したメッセージが含まれます。
     * <p>
     * このメソッドを呼び出す前に、{@link BeanMetaDataFactory}のコンポーネントがS2コンテナに登録されている必要があります。
     * </p>
     * 
     * @param message
     *            メッセージ
     * @param expected
     *            期待値
     * @param bean
     *            実際値
     */
    public static void assertBeanEquals(String message, DataSet expected,
            Object bean) {
        S2DaoBeanReader reader = new S2DaoBeanReader(bean,
                createBeanMetaData(bean.getClass()));
        assertEquals(message, expected, reader.read());
    }

    /**
     * データセットとビーンのリストが等しいことをアサートします。
     * <p>
     * このメソッドを呼び出す前に、{@link BeanMetaDataFactory}のコンポーネントがS2コンテナに登録されている必要があります。
     * </p>
     * 
     * @param expected
     *            期待値
     * @param list
     *            実際値
     */
    public static void assertBeanEquals(DataSet expected, List<?> list) {
        assertBeanEquals(null, expected, list);
    }

    /**
     * データセットとビーンのリストが等しいことをアサートします。もし等しくない場合、エラー情報に指定したメッセージが含まれます。
     * <p>
     * このメソッドを呼び出す前に、{@link BeanMetaDataFactory}のコンポーネントがS2コンテナに登録されている必要があります。
     * </p>
     * 
     * @param message
     *            メッセージ
     * @param expected
     *            期待値
     * @param list
     *            実際値
     */
    public static void assertBeanEquals(String message, DataSet expected,
            List<?> list) {
        S2DaoBeanListReader reader = new S2DaoBeanListReader(list,
                createBeanMetaData(list.get(0).getClass()));
        assertEquals(message, expected, reader.read());
    }

    /**
     * {@link BeanMetaData}を作成します。
     * 
     * @param beanClass
     *            Beanのクラス
     * @return {@link BeanMetaData}
     */
    protected static BeanMetaData createBeanMetaData(Class<?> beanClass) {
        final BeanMetaDataFactory factory = getBeanMetaDataFactory();
        return factory.createBeanMetaData(beanClass);
    }

    /**
     * {@link BeanMetaDataFactory}を返します。
     * 
     * @return {@link BeanMetaDataFactory}
     */
    protected static BeanMetaDataFactory getBeanMetaDataFactory() {
        return (BeanMetaDataFactory) SingletonS2ContainerFactory.getContainer()
                .getComponent(BeanMetaDataFactory.class);
    }

    /**
     * {@link S2TestCase}のアダプタです。
     * 
     * @author taedium
     */
    protected static class S2TestCaseAdapter extends S2TestCase {

        @SuppressWarnings("unchecked")
        @Override
        protected void assertMapEquals(String message, DataSet expected, Map map) {
            super.assertMapEquals(message, expected, map);
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void assertMapListEquals(String message, DataSet expected,
                List list) {
            super.assertMapListEquals(message, expected, list);
        }

    }

}
