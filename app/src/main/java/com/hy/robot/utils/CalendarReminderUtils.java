package com.hy.robot.utils;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.AlarmClock;
import android.provider.CalendarContract;
import android.text.TextUtils;
import androidx.annotation.RequiresApi;
import com.com1075.library.base.BaseActivity;
import com.hy.robot.App;
import com.orhanobut.logger.Logger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CalendarReminderUtils {

    private static String CALENDER_URL = "content://com.android.calendar/calendars";
    private static String CALENDER_EVENT_URL = "content://com.android.calendar/events";
    private static String CALENDER_REMINDER_URL = "content://com.android.calendar/reminders";

    private static String CALENDARS_NAME = "boohee";
    private static String CALENDARS_ACCOUNT_NAME = "BOOHEE@boohee.com";
    private static String CALENDARS_ACCOUNT_TYPE = "com.android.boohee";
    private static String CALENDARS_DISPLAY_NAME = "BOOHEE账户";

    /**
     * 检查是否已经添加了日历账户，如果没有添加先添加一个日历账户再查询
     * 获取账户成功返回账户id，否则返回-1
     */
    private static int checkAndAddCalendarAccount(Context context) {
        int oldId = checkCalendarAccount(context);
        if (oldId >= 0) {
            return oldId;
        } else {
            long addId = addCalendarAccount(context);
            if (addId >= 0) {
                return checkCalendarAccount(context);
            } else {
                return -1;
            }
        }
    }

    /**
     * 检查是否存在现有账户，存在则返回账户id，否则返回-1
     */
    private static int checkCalendarAccount(Context context) {
        Cursor userCursor = context.getContentResolver().query(Uri.parse(CALENDER_URL), null, null, null, null);
        try {
            if (userCursor == null) { //查询返回空值
                return -1;
            }
            int count = userCursor.getCount();
            if (count > 0) { //存在现有账户，取第一个账户的id返回
                userCursor.moveToFirst();
                return userCursor.getInt(userCursor.getColumnIndex(CalendarContract.Calendars._ID));
            } else {
                return -1;
            }
        } finally {
            if (userCursor != null) {
                userCursor.close();
            }
        }
    }

    /**
     * 添加日历账户，账户创建成功则返回账户id，否则返回-1
     */
    private static long addCalendarAccount(Context context) {
        TimeZone timeZone = TimeZone.getDefault();
        ContentValues value = new ContentValues();
        value.put(CalendarContract.Calendars.NAME, CALENDARS_NAME);
        value.put(CalendarContract.Calendars.ACCOUNT_NAME, CALENDARS_ACCOUNT_NAME);
        value.put(CalendarContract.Calendars.ACCOUNT_TYPE, CALENDARS_ACCOUNT_TYPE);
        value.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, CALENDARS_DISPLAY_NAME);
        value.put(CalendarContract.Calendars.VISIBLE, 1);
        value.put(CalendarContract.Calendars.CALENDAR_COLOR, Color.BLUE);
        value.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, CalendarContract.Calendars.CAL_ACCESS_OWNER);
        value.put(CalendarContract.Calendars.SYNC_EVENTS, 1);
        value.put(CalendarContract.Calendars.CALENDAR_TIME_ZONE, timeZone.getID());
        value.put(CalendarContract.Calendars.OWNER_ACCOUNT, CALENDARS_ACCOUNT_NAME);
        value.put(CalendarContract.Calendars.CAN_ORGANIZER_RESPOND, 0);

        Uri calendarUri = Uri.parse(CALENDER_URL);
        calendarUri = calendarUri.buildUpon()
                .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, CALENDARS_ACCOUNT_NAME)
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, CALENDARS_ACCOUNT_TYPE)
                .build();

        Uri result = context.getContentResolver().insert(calendarUri, value);
        long id = result == null ? -1 : ContentUris.parseId(result);
        return id;
    }

    /**
     * 添加日历事件
     */
    public static void addCalendarEvent(Context context, String title, String description, String reminderTime, int previousDate, String repeat) {
        try {
            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String str = "2019-09-25T08:00:00";
            Date date = sim.parse(reminderTime.replace("T", " "));
            if (context == null) {
                return;
            }
            int calId = checkAndAddCalendarAccount(context); //获取日历账户的id
            if (calId < 0) { //获取账户id失败直接返回，添加日历事件失败

                return;
            }

            //添加日历事件
            Calendar mCalendar = Calendar.getInstance();
            mCalendar.setTimeInMillis(date.getTime());//设置开始时间
            long start = mCalendar.getTime().getTime();
            mCalendar.setTimeInMillis(start + 10 * 60 * 1000);//设置终止时间，开始时间加10分钟
            long end = mCalendar.getTime().getTime();
            ContentValues event = new ContentValues();
            event.put("title", title);
            event.put("description", description);
            event.put("calendar_id", calId); //插入账户的id
            event.put(CalendarContract.Events.DTSTART, start);
            event.put(CalendarContract.Events.DTEND, end);
            event.put(CalendarContract.Events.HAS_ALARM, 1);//设置有闹钟提醒
            event.put(CalendarContract.Events.EVENT_TIMEZONE, "Asia/Shanghai");//这个是时区，必须有
            if (repeat.contains("EVERYDAY")) {
                event.put(CalendarContract.Events.RRULE, "FREQ=DAILY;WKST=SU");
            } else if (repeat.contains("W")) {//每周
                event.put(CalendarContract.Events.RRULE, "FREQ=WEEKLY;WKST=SU;BYDAY=" + replaceWeek(repeat));
            } else if (repeat.contains("M")) {
                Logger.e(repeat.replace("M", ""));
                event.put(CalendarContract.Events.RRULE, "FREQ=MONTHLY;WKST=SU;BYMONTHDAY=" + repeat.replace("M", ""));
            }

            Uri newEvent = context.getContentResolver().insert(Uri.parse(CALENDER_EVENT_URL), event); //添加事件
            if (newEvent == null) { //添加日历事件失败直接返回
                return;
            }
            //事件提醒的设定
            ContentValues values = new ContentValues();
            values.put(CalendarContract.Reminders.EVENT_ID, ContentUris.parseId(newEvent));
            values.put(CalendarContract.Reminders.MINUTES, previousDate);// 提前previousDate天有提醒
            values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
            Uri uri = context.getContentResolver().insert(Uri.parse(CALENDER_REMINDER_URL), values);
            if (uri == null) { //添加事件提醒失败直接返回
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void createAlarm(BaseActivity activity, String repeat, String message, String time, int resId) {
        try {
            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sim.parse(time.replace("T", " "));

            ArrayList<Integer> testDays = new ArrayList<>();
            if (repeat.contains("1")) {
                testDays.add(Calendar.MONDAY);//周一
            } else if (repeat.contains("2")) {
                testDays.add(Calendar.TUESDAY);//周二
            } else if (repeat.contains("3")) {
                testDays.add(Calendar.WEDNESDAY);//周三
            } else if (repeat.contains("4")) {
                testDays.add(Calendar.THURSDAY);//周四
            } else if (repeat.contains("5")) {
                testDays.add(Calendar.FRIDAY);//周五
            } else if (repeat.contains("6")) {
                testDays.add(Calendar.SATURDAY);//周六
            } else if (repeat.contains("7")) {
                testDays.add(Calendar.SUNDAY);//周日
            } else if (repeat.contains("EVERYDAY")) {
                testDays.add(Calendar.MONDAY);//周一
                testDays.add(Calendar.TUESDAY);//周二
                testDays.add(Calendar.WEDNESDAY);//周三
                testDays.add(Calendar.THURSDAY);//周四
                testDays.add(Calendar.SATURDAY);//周六
                testDays.add(Calendar.FRIDAY);//周五
                testDays.add(Calendar.SUNDAY);//周日
            }


            String packageName = App.getContext().getPackageName();
            Uri ringtoneUri = Uri.parse("android.resource://" + packageName + "/" + resId);

            Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                    //闹钟的小时
                    .putExtra(AlarmClock.EXTRA_HOUR, date.getHours())
                    //闹钟的分钟
                    .putExtra(AlarmClock.EXTRA_MINUTES, date.getMinutes())
                    //响铃时提示的信息
                    .putExtra(AlarmClock.EXTRA_MESSAGE, message)
                    //用于指定该闹铃触发时是否振动
                    .putExtra(AlarmClock.EXTRA_VIBRATE, true)
                    //一个 content: URI，用于指定闹铃使用的铃声，也可指定 VALUE_RINGTONE_SILENT 以不使用铃声。
                    //如需使用默认铃声，则无需指定此 extra。
                    .putExtra(AlarmClock.EXTRA_RINGTONE, ringtoneUri)
                    //对于一次性闹铃，无需指定此 extra
                    .putExtra(AlarmClock.EXTRA_DAYS, testDays)
                    //如果为true，则调用startActivity()不会进入手机的闹钟设置界面
                    .putExtra(AlarmClock.EXTRA_SKIP_UI, true);
            if (intent.resolveActivity(activity.getPackageManager()) != null) {
                activity.startActivity(intent);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    private static String replaceWeek(String repeat) {
        if (repeat.contains("1")) {
            return "MO";
        } else if (repeat.contains("2")) {
            return "TU";
        } else if (repeat.contains("3")) {
            return "WE";
        } else if (repeat.contains("4")) {
            return "TH";
        } else if (repeat.contains("5")) {
            return "FR";
        } else if (repeat.contains("6")) {
            return "SA";
        } else if (repeat.contains("7")) {
            return "SU";
        }
        return "";

    }


    /**
     * 删除日历事件
     */
    public static void deleteCalendarEvent(Context context, String title) {
        if (context == null) {
            return;
        }
        Cursor eventCursor = context.getContentResolver().query(Uri.parse(CALENDER_EVENT_URL), null, null, null, null);
        try {
            if (eventCursor == null) { //查询返回空值
                return;
            }
            if (eventCursor.getCount() > 0) {
                //遍历所有事件，找到title跟需要查询的title一样的项
                for (eventCursor.moveToFirst(); !eventCursor.isAfterLast(); eventCursor.moveToNext()) {
                    String eventTitle = eventCursor.getString(eventCursor.getColumnIndex("title"));
                    if (!TextUtils.isEmpty(title) && title.equals(eventTitle)) {
                        int id = eventCursor.getInt(eventCursor.getColumnIndex(CalendarContract.Calendars._ID));//取得id
                        Uri deleteUri = ContentUris.withAppendedId(Uri.parse(CALENDER_EVENT_URL), id);
                        int rows = context.getContentResolver().delete(deleteUri, null, null);
                        if (rows == -1) { //事件删除失败
                            return;
                        }
                    }
                }
            }
        } finally {
            if (eventCursor != null) {
                eventCursor.close();
            }
        }
    }

}
