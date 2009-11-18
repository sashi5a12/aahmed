delete from AIMS_EVENT_HANDLERS aeh where aeh.EVENT_ID in (2022, 2023, 2024, 2025, 2026)
/
delete from AIMS_EVENT_PLACE_HOLDERS aeph where aeph.EVENT_ID in (2022, 2023, 2024, 2025, 2026)
/
delete from AIMS_NOTIF_AD_HOC_RECIPIENTS anahr where anahr.NOTIFICATION_ID in (select aen.NOTIFICATION_ID 
	from AIMS_EVENT_NOTIFICATIONS aen where aen.EVENT_ID in (2022, 2023, 2024, 2025, 2026))
/
delete from AIMS_EVENT_NOTIFICATIONS aen where aen.EVENT_ID in (2022, 2023, 2024, 2025, 2026)
/
delete from AIMS_EVENTS ae where ae.EVENT_ID in (2022, 2023, 2024, 2025, 2026)
/
delete from AIMS_EVENT_HANDLERS aeh where aeh.EVENT_ID in (2014)
/
delete from AIMS_EVENT_PLACE_HOLDERS aeph where aeph.EVENT_ID in (2014)
/
delete from AIMS_NOTIF_AD_HOC_RECIPIENTS anahr where anahr.NOTIFICATION_ID in (select aen.NOTIFICATION_ID 
	from AIMS_EVENT_NOTIFICATIONS aen where aen.EVENT_ID in (2014))
/
delete from AIMS_EVENT_NOTIFICATIONS aen where aen.EVENT_ID in (2014)
/
delete from AIMS_EVENTS ae where ae.EVENT_ID in (2014)
/