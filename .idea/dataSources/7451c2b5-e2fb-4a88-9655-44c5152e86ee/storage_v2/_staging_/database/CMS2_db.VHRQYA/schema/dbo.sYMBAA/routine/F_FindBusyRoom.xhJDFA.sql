--用于查找一定时间内有课的课室,返回所有有课的课室的ID
CREATE PROC dbo.F_FindBusyRoom(
	@Week TINYINT,
	@ClassWeek INTEGER,
	@ClassTime SMALLINT
) AS BEGIN
SET NOCOUNT ON
SELECT DISTINCT C1.RID FROM dbo.CourseInfo AS C1 WHERE C1.CWeek=@Week AND C1.CClWeek&@ClassWeek<>0 AND C1.CClTime&@ClassTime<>0
END
GO