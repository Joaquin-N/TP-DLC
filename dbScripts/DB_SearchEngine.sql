USE [master]
GO
/****** Object:  Database [SearchEngine]    Script Date: 11/4/2021 19:43:22 ******/
CREATE DATABASE [SearchEngine]
GO
USE [SearchEngine]
GO
/****** Object:  Table [dbo].[Documentos]    Script Date: 11/4/2021 19:43:22 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Documentos](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[documento] [varchar](50) NOT NULL,
 CONSTRAINT [PK_Documentos] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Palabras]    Script Date: 11/4/2021 19:43:22 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Palabras](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[palabra] [varchar](50) NOT NULL,
 CONSTRAINT [PK_Palabras] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Posteo]    Script Date: 11/4/2021 19:43:22 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Posteo](
	[id_palabra] [int] NOT NULL,
	[id_documento] [int] NOT NULL,
	[tf] [int] NOT NULL,
    CONSTRAINT [PK_Posteo] PRIMARY KEY (id_palabra, id_documento))

GO
ALTER TABLE [dbo].[Posteo]  WITH CHECK ADD CONSTRAINT [FK_Posteo_Documento] FOREIGN KEY([id_documento])
REFERENCES [dbo].[Documentos] ([id])
GO
ALTER TABLE [dbo].[Posteo]  WITH CHECK ADD CONSTRAINT [FK_Posteo_Palabra] FOREIGN KEY([id_palabra])
REFERENCES [dbo].[Palabras] ([id])
ALTER TABLE [dbo].[Posteo] CHECK CONSTRAINT [FK_Posteo_Documento]
GO
USE [master]
GO
ALTER DATABASE [SearchEngine] SET  READ_WRITE 
GO