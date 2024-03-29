USE [master]
GO
/****** Object:  Database [AnimeSuggestion]    Script Date: 25-Mar-19 8:48:49 PM ******/
CREATE DATABASE [AnimeSuggestion]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'AnimeSuggestion', FILENAME = N'D:\ProgramFiles\MSSQL14.MSSQLSERVER\MSSQL\DATA\AnimeSuggestion.mdf' , SIZE = 335872KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'AnimeSuggestion_log', FILENAME = N'D:\ProgramFiles\MSSQL14.MSSQLSERVER\MSSQL\DATA\AnimeSuggestion_log.ldf' , SIZE = 1253376KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
GO
ALTER DATABASE [AnimeSuggestion] SET COMPATIBILITY_LEVEL = 140
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [AnimeSuggestion].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [AnimeSuggestion] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [AnimeSuggestion] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [AnimeSuggestion] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [AnimeSuggestion] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [AnimeSuggestion] SET ARITHABORT OFF 
GO
ALTER DATABASE [AnimeSuggestion] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [AnimeSuggestion] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [AnimeSuggestion] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [AnimeSuggestion] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [AnimeSuggestion] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [AnimeSuggestion] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [AnimeSuggestion] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [AnimeSuggestion] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [AnimeSuggestion] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [AnimeSuggestion] SET  DISABLE_BROKER 
GO
ALTER DATABASE [AnimeSuggestion] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [AnimeSuggestion] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [AnimeSuggestion] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [AnimeSuggestion] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [AnimeSuggestion] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [AnimeSuggestion] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [AnimeSuggestion] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [AnimeSuggestion] SET RECOVERY FULL 
GO
ALTER DATABASE [AnimeSuggestion] SET  MULTI_USER 
GO
ALTER DATABASE [AnimeSuggestion] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [AnimeSuggestion] SET DB_CHAINING OFF 
GO
ALTER DATABASE [AnimeSuggestion] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [AnimeSuggestion] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [AnimeSuggestion] SET DELAYED_DURABILITY = DISABLED 
GO
EXEC sys.sp_db_vardecimal_storage_format N'AnimeSuggestion', N'ON'
GO
ALTER DATABASE [AnimeSuggestion] SET QUERY_STORE = OFF
GO
USE [AnimeSuggestion]
GO
/****** Object:  Table [dbo].[Category]    Script Date: 25-Mar-19 8:48:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Category](
	[Name] [nvarchar](50) NOT NULL,
	[Value] [nvarchar](250) NULL,
 CONSTRAINT [PK_Category] PRIMARY KEY CLUSTERED 
(
	[Name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Movie]    Script Date: 25-Mar-19 8:48:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Movie](
	[id] [uniqueidentifier] NOT NULL,
	[img] [nvarchar](max) NULL,
	[name] [nvarchar](500) NOT NULL,
	[mountOfComment] [bigint] NULL,
	[mountOfViewed] [bigint] NULL,
	[rateManga] [float] NULL,
	[isTopMovie] [bit] NULL,
	[year] [int] NULL,
	[episode] [int] NULL,
	[description] [nvarchar](max) NULL,
	[rateInSite] [float] NULL,
 CONSTRAINT [PK_Table_1] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[movieMappingCate]    Script Date: 25-Mar-19 8:48:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[movieMappingCate](
	[movieId] [uniqueidentifier] NOT NULL,
	[nameCate] [nvarchar](50) NOT NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[RawData]    Script Date: 25-Mar-19 8:48:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[RawData](
	[id] [uniqueidentifier] NOT NULL,
	[img] [nvarchar](max) NULL,
	[name] [nvarchar](500) NOT NULL,
	[mountOfComment] [bigint] NULL,
	[mountOfViewed] [bigint] NULL,
	[rateManga] [float] NULL,
	[isTopMovie] [bit] NULL,
	[year] [int] NULL,
	[episode] [int] NULL,
	[description] [nvarchar](max) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
ALTER TABLE [dbo].[movieMappingCate]  WITH CHECK ADD  CONSTRAINT [FK_movieMappingCate_Category] FOREIGN KEY([nameCate])
REFERENCES [dbo].[Category] ([Name])
GO
ALTER TABLE [dbo].[movieMappingCate] CHECK CONSTRAINT [FK_movieMappingCate_Category]
GO
ALTER TABLE [dbo].[movieMappingCate]  WITH CHECK ADD  CONSTRAINT [FK_movieMappingCate_Movie] FOREIGN KEY([movieId])
REFERENCES [dbo].[Movie] ([id])
GO
ALTER TABLE [dbo].[movieMappingCate] CHECK CONSTRAINT [FK_movieMappingCate_Movie]
GO
/****** Object:  StoredProcedure [dbo].[GETMOVIESBYNAMECATE]    Script Date: 25-Mar-19 8:48:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[GETMOVIESBYNAMECATE]
	-- Add the parameters for the stored procedure here
	@NameCate nvarchar(50),
	@Page int,
	@PageSize int
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	SELECT Movie.id,Movie.img,Movie.name,Movie.rateInSite,Movie.episode
	from movieMappingCate mappings
	INNER JOIN Movie movie On movie.id = mappings.movieId
	where mappings.nameCate = @NameCate
	Order by movie.rateInSite desc
	offset @Page rows fetch first @PageSize row only
END
GO
/****** Object:  StoredProcedure [dbo].[GETSUGGESTIONANIME]    Script Date: 25-Mar-19 8:48:50 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[GETSUGGESTIONANIME] 
	-- Add the parameters for the stored procedure here
	@CateName1 nvarchar(50),@CateName2 nvarchar(50)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	Select TOP 1 data.id from 
	(SELECT * from movieMappingCate mappings
	Inner Join Movie movie on mappings.movieId = movie.id
	where mappings.nameCate = @CateName1 or mappings.nameCate = @CateName2) as data
	Group by data.id,data.rateInSite
	Having COUNT(*) > 1
	Order By NEWID()
END
GO
USE [master]
GO
ALTER DATABASE [AnimeSuggestion] SET  READ_WRITE 
GO
