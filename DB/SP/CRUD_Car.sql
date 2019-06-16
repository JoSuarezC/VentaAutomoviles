USE [FactoryDB];
GO

IF OBJECT_ID('[dbo].[usp_CarSelect]') IS NOT NULL
BEGIN 
    DROP PROC [dbo].[usp_CarSelect] 
END 
GO
CREATE PROC [dbo].[usp_CarSelect] 
    @car_id int = NULL
AS 
	SET NOCOUNT ON 
	SET XACT_ABORT ON  

	SELECT c.[car_id], c.[carBrand_id], c.[carType_id], c.[model], c.[engine], c.[year], c.[seats], 
	c2.[doors], c2.[fuelType_id], c2.[acceleration], c2.[maximum_speed], c2.[price], c2.[photo], c2.production_date
	FROM   [dbo].[Car] c
	inner join [DESKTOP-3N2P4FH\FACTORYINSTANCE2].FactoryDB.dbo.Car c2 on c2.car_id = c.car_id
	WHERE  (c.[car_id] = @car_id OR @car_id IS NULL) 

GO
/*
	INSERT INTO [DESKTOP-3N2P4FH\FACTORYINSTANCE2].FactoryDB.dbo.Car 
	([car_id], [doors], [fuelType_id], [acceleration], [maximum_speed], [price], [photo], [production_date])
	SELECT 3543, 1, 1, 1, 1, 1, null, null

EXEC ('EXECUTE FactoryDB.dbo.[usp_CarInsert] 
		@car_id=?, @doors=?, @fuelType_id=?, @acceleration=?, @maximum_speed=?, @price=?, @photo=?, @production_date=?', 
		200, 1, 1, 1, 1, 1, NULL, '2019-08-16') AT [DESKTOP-3N2P4FH\FACTORYINSTANCE2]*/

EXEC [dbo].[usp_CarInsert] 1,1,'asd','ads',200,1,1,1,1,1,1,NULL,1,1

IF OBJECT_ID('[dbo].[usp_CarInsert]') IS NOT NULL
BEGIN 
    DROP PROC [dbo].[usp_CarInsert] 
END 
GO
CREATE PROC [dbo].[usp_CarInsert] 
    @carBrand_id int = NULL,
    @carType_id int = NULL,
    @model nvarchar(50) = NULL,
    @engine nvarchar(50) = NULL,
    @year int = NULL,
    @seats int = NULL,
    @doors int = NULL,
    @fuelType_id int = NULL,
    @acceleration float = NULL,
    @maximum_speed float = NULL,
    @price money = NULL,
    @photo image = NULL,
	@factoryID int = NULL,
	@quantity int = NULL
AS 
	SET NOCOUNT ON 
	SET XACT_ABORT ON  
	
	BEGIN TRAN
	
	INSERT INTO [dbo].[Car] ([carBrand_id], [carType_id], [model], [engine], [year], [seats])
	SELECT @carBrand_id, @carType_id, @model, @engine, @year, @seats

	DECLARE @lastCar_id int
	SET @lastCar_id = (SELECT [car_id] FROM [dbo].[Car] WHERE [car_id] = SCOPE_IDENTITY());

	DECLARE @currentDate date
	SET @currentDate = (SELECT GETDATE());		
			
	INSERT INTO [DESKTOP-3N2P4FH\FACTORYINSTANCE2].FactoryDB.dbo.Car 
	([car_id], [doors], [fuelType_id], [acceleration], [maximum_speed], [price], [photo], [production_date])
	SELECT @lastCar_id, @doors, @fuelType_id, @acceleration, @maximum_speed, @price, @photo,  @currentDate

	/*EXEC ('EXECUTE FactoryDB.dbo.[usp_CarInsert] 
		@car_id=?, @doors=?, @fuelType_id=?, @acceleration=?, @maximum_speed=?, @price=?, @photo=?, @production_date=?', 
		@lastCar_id, @doors, @fuelType_id, @acceleration, @maximum_speed, @price, @photo, @currentDate) AT [DESKTOP-3N2P4FH\FACTORYINSTANCE2]*/

--	EXEC [dbo].[usp_Factory-CarInsert] @car_id = @lastCar_id, @factory_id = @factoryID, @quantity = @quantity

    SELECT c.[car_id], c.[carBrand_id], c.[carType_id], c.[model], c.[engine], c.[year], c.[seats], 
	c2.[doors], c2.[fuelType_id], c2.[acceleration], c2.[maximum_speed], c2.[price], c2.[photo], c2.production_date
	FROM   [dbo].[Car] c
	inner join [DESKTOP-3N2P4FH\FACTORYINSTANCE2].FactoryDB.dbo.Car c2 on c2.[car_id] = c.[car_id]
	WHERE  c.[car_id] = @lastCar_id
	        
	COMMIT
GO

IF OBJECT_ID('[dbo].[usp_CarUpdate]') IS NOT NULL
BEGIN 
    DROP PROC [dbo].[usp_CarUpdate] 
END 
GO
CREATE PROC [dbo].[usp_CarUpdate] 
    @car_id int,
    @carBrand_id int = NULL,
    @carType_id int = NULL,
    @model nvarchar(50) = NULL,
    @engine nvarchar(50) = NULL,
    @year int = NULL,
    @seats int = NULL,
    @doors int = NULL,
    @fuelType_id int = NULL,
    @acceleration float = NULL,
    @maximum_speed float = NULL,
    @price money = NULL,
    @photo image = NULL,
	@productionDate date = NULL
AS 
	SET NOCOUNT ON 
	SET XACT_ABORT ON  
	
	BEGIN TRAN

	UPDATE [dbo].[Car]
	SET    [carBrand_id] = ISNULL(@carBrand_id, [carBrand_id]), [carType_id] = ISNULL(@carType_id,[carType_id]), [model] = ISNULL(@model,[model]), [engine] = ISNULL(@engine,[engine]), [year] = ISNULL(@year,[year]), [seats] = ISNULL(@seats,[seats])
	WHERE  [car_id] = @car_id
												
	EXEC ('EXECUTE FactoryDB.dbo.[usp_CarUpdate] 
		@car_id=?, @doors=?, @fuelType_id=?, @acceleration=?, @maximum_speed=?, @price=?, @photo=?, @production_date=?', 
		@car_id, @doors, @fuelType_id, @acceleration, @maximum_speed, @price, @photo, @productionDate) AT [DESKTOP-3N2P4FH\FACTORYINSTANCE2]

	SELECT c.[car_id], c.[carBrand_id], c.[carType_id], c.[model], c.[engine], c.[year], c.[seats], 
	c2.[doors], c2.[fuelType_id], c2.[acceleration], c2.[maximum_speed], c2.[price], c2.[photo], c2.production_date
	FROM   [dbo].[Car] c
	inner join [DESKTOP-3N2P4FH\FACTORYINSTANCE2].FactoryDB.dbo.Car c2 on c2.car_id = c.car_id
	WHERE  (c.[car_id] = @car_id OR @car_id IS NULL) 

	COMMIT
GO
IF OBJECT_ID('[dbo].[usp_CarDelete]') IS NOT NULL
BEGIN 
    DROP PROC [dbo].[usp_CarDelete] 
END 
GO
CREATE PROC [dbo].[usp_CarDelete] 
    @car_id int
AS 
	SET NOCOUNT ON 
	SET XACT_ABORT ON  
	
	BEGIN TRAN

	DELETE
	FROM   [dbo].[Car]
	WHERE  [car_id] = @car_id

	COMMIT
GO
----------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------

