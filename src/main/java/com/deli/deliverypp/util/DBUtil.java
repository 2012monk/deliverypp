package com.deli.deliverypp.util;

import com.deli.deliverypp.DB.DeliUser;
import com.deli.deliverypp.model.OrderInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.StringJoiner;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DBUtil {

    private static final Logger log = Logger.getGlobal();
    private ObjectMapper mapper;

    public DBUtil() {
    }

    public DBUtil(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public static String convertClassNameToDbName (String input) {
        String p = "[A-Z][a-z]*";
        Pattern pattern = Pattern.compile(p);
        Matcher matcher = pattern.matcher(input);

        ArrayList<String> list = new ArrayList<>();
        while (matcher.find()){
            list.add(matcher.group().toUpperCase(Locale.ROOT));
        }
        return String.join("_", list);
    }

    public static String convertToDbNameConvention(String input){
        String p = "[a-z]*[a-z]|[A-Z][a-z]*";
//        Pattern pattern = Pattern.compile("[a-z]*[a-z]|[A-Z]*[a-z]");
        Pattern pattern = Pattern.compile(p);
        Matcher matcher = pattern.matcher(input);

        ArrayList<String> list = new ArrayList<>();
        while (matcher.find()) {
            list.add(matcher.group().toUpperCase(Locale.ROOT));
        }

        String output = String.join("_", list);
        if (output.equals("")) {
            throw new IllegalArgumentException("IllegalArgument Input");
        }

        return output;
    }

    public static String convertToCamelCase(String input) {
        String i = input.toLowerCase(Locale.ROOT);
        Pattern pattern = Pattern.compile("_[a-z]");
        Matcher matcher = pattern.matcher(i);
        while (matcher.find()) {
            i = i.replaceAll(matcher.group(), matcher.group().substring(1).toUpperCase(Locale.ROOT));
        }
        return i;
    }


    // hint generate pojo from resultSet
    public static <T> T setPOJO(Class<T> target, ResultSet rs)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        Constructor<T> constructor = target.getConstructor();
        T out = target.newInstance();

        Field[] fields = out.getClass().getDeclaredFields();
        Field[] fs = target.getDeclaredFields();
//        if (rs.next()){
        for (Field f: fields) {
            f.setAccessible(true);
            String n = convertToDbNameConvention(f.getName());
//            System.out.println(n+" : " );
            try {
                if (f.getType().equals(String.class)) {
//                    log.info(rs.getString(n));
                    f.set(out, rs.getString(n));
                }

                else if (f.getType().equals(int.class)) {
                    f.set(out, rs.getInt(n));
                }

                else if (f.getType().equals(double.class)) {
                    f.set(out, rs.getDouble(n));
                }

                else if (f.getType().equals(long.class)) {
                    f.set(out, rs.getLong(n));
                }

                else if (f.getType().equals(BigDecimal.class)) {
                    f.set(out, rs.getBigDecimal(n));
                }

                else if (f.getType().isEnum()){
                    f.set(out, Enum.valueOf((Class<Enum>) f.getType(),rs.getString(n)));
                }
                else {
//                        System.out.println(n+rs.getString(n));
//                        f.set(out, rs.getString(n));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
//        }

        return out;
    }

    // HINT generate sql statement line
    public static <T> String makeInsertSql(T target) {
        String tableName = convertClassNameToDbName(target.getClass().getSimpleName());
//        String sql = "INSERT INTO " + tableName;
        Field[] fields = target.getClass().getDeclaredFields();
        StringJoiner args = new StringJoiner(",","(",")");
        StringJoiner names = new StringJoiner(",","(",")");

        StringBuilder sql = new StringBuilder();
        Arrays.stream(fields).forEach(f -> {
            f.setAccessible(true);
            try {
                Object obj = f.get(target);
                if (obj != null) {
                    StringJoiner sj = new StringJoiner("","'","'");
                    sj.add(String.valueOf(f.get(target)));
                    args.add(sj.toString());
//                    args.add(String.valueOf(f.get(target)));
                    names.add(convertToDbNameConvention(f.getName()));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        sql.append("INSERT INTO").append(" ")
                .append(tableName).append(" ")
                .append(names).append(" ")
                .append("VALUES").append(" ")
                .append(args).append(" ");

//        System.out.println(sql);


        return sql.toString();

    }

    // NOTE find map value and map it
    // NOTE can not handle sub query yet
    public static <T> PreparedStatement executeSql (T target,Connection conn, String sql) {
        Field[] fields = target.getClass().getDeclaredFields();
        String parsedSql = sql.toUpperCase(Locale.ROOT);

        return null;
    }

    private static <T> PreparedStatement executeInsertSql (T target, Connection conn, String sql) {
        return null;
    }

    private static <T> PreparedStatement executeSelectSql (T target, Connection conn, String sql) {
        String parsed = sql.toUpperCase(Locale.ROOT);
        Pattern valPattern = Pattern.compile("[?]");

        Pattern v1Pattern = Pattern.compile("[(][A-Z]*[)]");
        Matcher v1Matcher = v1Pattern.matcher(parsed);
        // NOTE dived into two cases

        // NOTE 1. pattern (k,k,k,k,) (?,?,?,?,)
        // NOTE 2. pattern k=?,k=?

        Matcher matcher = valPattern.matcher(parsed);
        int cnt = matcher.groupCount();
        return null;
    }








    // TODO make it
    public static <T> String makeSelectSql (T target, String key) {
        return null;
    }

    public static <T> Statement makeInsertStatement (T target, Connection conn) throws SQLException {
        String sql = makeInsertSql(target);
        return conn.prepareStatement(sql);
    }

    public static <T> Statement makeSelectStatement (T target, Connection conn) throws SQLException {
        return null;
    }

    public static <T> T getObjectFromStatement() {
        return null;
    }


    // TODO Generic 캐스팅 알아보기!
    public static <T extends Enum<T>> Enum<T> setEnumFromString(String target, Class<? extends Enum<T>> targetClass) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

//        Method m = targetClass.getDeclaredMethod("valueOf", Class.class, String.class);

        return Enum.valueOf( (Class<T>) targetClass, target);


    }

}
