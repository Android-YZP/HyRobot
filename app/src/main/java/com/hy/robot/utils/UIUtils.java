package com.hy.robot.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.provider.CalendarContract;
import android.widget.Toast;

import com.hy.robot.App;
import com.hy.robot.bean.NewsListBean;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class UIUtils {
    private static Toast mToast;

    public static Context getContext() {
        return App.getContext();
    }

    public static Toast showTip(String str) {
        if (mToast == null) {
            mToast = Toast.makeText(getContext(), str, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(str);
        }
        mToast.show();
        return mToast;
    }


    public static List<NewsListBean.ResultBean> chooseNewsList(List<NewsListBean.ResultBean> list) {
        List<NewsListBean.ResultBean> chooseList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getTemplateType() == 3) {
                chooseList.add(list.get(i));
            }
        }

        return chooseList;
    }

//
//
//    /**
//     * 增加日历提醒事件
//     * @param activity
//     * @param dataEntity
//     * @param isRecord
//     * @return
//     */
//    public static boolean addCalendarReminder(Activity activity, DataEntity dataEntity, boolean isRecord) {
//
//
//
//        try {
//
////        Calendar startCalendar = Calendar.getInstance();
////        long startTime = startCalendar.getTimeInMillis() + 8 * 60 * 1000;
//            long startTime = dataEntity.getRemind_time();
////        Calendar endCalendar = Calendar.getInstance();
////        long endTime = endCalendar.getTimeInMillis() + 50 * 60 * 1000;
//            long endTime = startTime + 10 * 60 * 1000;
//            String remindTitle = dataEntity.getTitle();
//
//            // 日历事件
//            ContentValues calEvent = new ContentValues();
//            calEvent.put(CalendarContract.Events.CALENDAR_ID, 1); // XXX pick)
//            calEvent.put(CalendarContract.Events.TITLE, remindTitle);
//            calEvent.put(CalendarContract.Events.DTSTART, startTime);
//            calEvent.put(CalendarContract.Events.DTEND, endTime);
//            calEvent.put(CalendarContract.Events.HAS_ALARM, 1);
//            calEvent.put(CalendarContract.Events.EVENT_TIMEZONE, CalendarContract.Calendars.CALENDAR_TIME_ZONE);
//
//            ContentResolver cr = activity.getContentResolver();
//            if (null == cr) {
//                return false;
//            }
//            try {
//                Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, calEvent);
//                if (null == uri) {
//                    return false;
//                }
//                int eventId = Integer.parseInt(uri.getLastPathSegment());
//
//                /** 记录code 记录eventId **/
//                if (isRecord) {
//                    saveEventCode(dataEntity.getProduct_code());
//                    saveEventId(eventId);
//                }
//
//                // 日历提醒
//                ContentValues reminders = new ContentValues();
//                reminders.put(CalendarContract.Reminders.EVENT_ID, eventId);
//                reminders.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
//                reminders.put(CalendarContract.Reminders.MINUTES, REMIND_MINUTES);
//                cr.insert(CalendarContract.Reminders.CONTENT_URI, reminders);
//
//                return true;
//            } catch (SecurityException ignore) {}
//        } catch (Exception ignore) {}
//
//        return false;
//    }


}
