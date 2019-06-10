USE [FactoryDB];
GO

IF OBJECT_ID('[dbo].[usp_CarXAccessorySelect]') IS NOT NULL
BEGIN 
    DROP PROC [dbo].[usp_CarXAccessorySelect] 
END 
GO
CREATE PROC [dbo].[usp_CarXAccessorySelect] 
    @carXAccessory_id int
AS 
	SET NOCOUNT ON 
	SET XACT_ABORT ON  

	SELECT [carXAccessory_id], [car_id], [accessorie_id] 
	FROM   [dbo].[CarXAccessory] 
	WHERE  ([carXAccessory_id] = @carXAccessory_id OR @carXAccessory_id IS NULL) 
GO
IF OBJECT_ID('[dbo].[usp_CarXAccessoryInsert]') IS NOT NULL
BEGIN 
    DROP PROC [dbo].[usp_CarXAccessoryInsert] 
END 
GO
CREATE PROC [dbo].[usp_CarXAccessoryInsert] 
    @car_id int = NULL,
    @accessorie_id int = NULL
AS 
	SET NOCOUNT ON 
	SET XACT_ABORT ON  
	
	BEGIN TRAN
	
	INSERT INTO [dbo].[CarXAccessory] ([car_id], [accessorie_id])
	SELECT @car_id, @accessorie_id
	
	-- Begin Return Select <- do not remove
	SELECT [carXAccessory_id], [car_id], [accessorie_id]
	FROM   [dbo].[CarXAccessory]
	WHERE  [carXAccessory_id] = SCOPE_IDENTITY()
	-- End Return Select <- do not remove
               
	COMMIT
GO
IF OBJECT_ID('[dbo].[usp_CarXAccessoryUpdate]') IS NOT NULL
BEGIN 
    DROP PROC [dbo].[usp_CarXAccessoryUpdate] 
END 
GO
CREATE PROC [dbo].[usp_CarXAccessoryUpdate] 
    @carXAccessory_id int,
    @car_id int = NULL,
    @accessorie_id int = NULL
AS 
	SET NOCOUNT ON 
	SET XACT_ABORT ON  
	
	BEGIN TRAN

	UPDATE [dbo].[CarXAccessory]
	SET    [car_id] = @car_id, [accessorie_id] = @accessorie_id
	WHERE  [carXAccessory_id] = @carXAccessory_id
	
	-- Begin Return Select <- do not remove
	SELECT [carXAccessory_id], [car_id], [accessorie_id]
	FROM   [dbo].[CarXAccessory]
	WHERE  [carXAccessory_id] = @carXAccessory_id	
	-- End Return Select <- do not remove

	COMMIT
GO
IF OBJECT_ID('[dbo].[usp_CarXAccessoryDelete]') IS NOT NULL
BEGIN 
    DROP PROC [dbo].[usp_CarXAccessoryDelete] 
END 
GO
CREATE PROC [dbo].[usp_CarXAccessoryDelete] 
    @carXAccessory_id int
AS 
	SET NOCOUNT ON 
	SET XACT_ABORT ON  
	
	BEGIN TRAN

	DELETE
	FROM   [dbo].[CarXAccessory]
	WHERE  [carXAccessory_id] = @carXAccessory_id

	COMMIT
GO
----------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------
