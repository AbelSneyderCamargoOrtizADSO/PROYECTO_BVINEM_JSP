-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 10-08-2024 a las 21:25:00
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `proyecto_bv`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_asignaturas`
--

CREATE TABLE `tb_asignaturas` (
  `id_asig` int(11) NOT NULL,
  `nom_asig` varchar(40) DEFAULT NULL,
  `estado` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_asignaturas`
--

INSERT INTO `tb_asignaturas` (`id_asig`, `nom_asig`, `estado`) VALUES
(1, 'Matemática pura', 0),
(2, 'Ciencias Naturales', 1),
(3, 'Lengua y Literatura', 1),
(4, 'Historia', 1),
(5, 'Geografía', 1),
(6, 'Arqueologia', 1),
(7, 'Informática', 1),
(8, 'Física', 1),
(9, 'Biologia', 1),
(10, 'Filosofía', 1),
(16, 'Lengua extranjera', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_documento`
--

CREATE TABLE `tb_documento` (
  `id_doc` int(11) NOT NULL,
  `titulo` varchar(80) NOT NULL,
  `autor` varchar(60) NOT NULL,
  `descripcion` text DEFAULT NULL,
  `fecha_carga` datetime NOT NULL,
  `doc_docente_fk` int(11) DEFAULT NULL,
  `id_asig_fk` int(11) DEFAULT NULL,
  `id_idioma_fk` int(11) DEFAULT NULL,
  `id_tipo_fk` int(11) DEFAULT NULL,
  `year_publi` int(11) DEFAULT NULL,
  `miniatura` varchar(255) DEFAULT NULL,
  `archivo` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_documento`
--

INSERT INTO `tb_documento` (`id_doc`, `titulo`, `autor`, `descripcion`, `fecha_carga`, `doc_docente_fk`, `id_asig_fk`, `id_idioma_fk`, `id_tipo_fk`, `year_publi`, `miniatura`, `archivo`) VALUES
(64, 'Tartarín de Tarascón adso', 'Daudet, Alphonse', 'Tartarín de Tarascón, el mitómano y fantasioso Tartarín, usando y aun abusando de los efectos que el espejismo produce en los calenturientos cerebros de los tarasconeses, se ha ganado fama de intrépido aventurero y hasta de audaz vapuleador de bandoleros chinos en Shangai.', '2024-08-02 00:38:24', 545554, 3, 1, 3, 2016, 'miniaturas\\tartarin.jpg', 'pdfs/jeje.pdf'),
(70, 'Pídeme lo que quieras, ahora y siempre', 'Megan Maxwell', '\"Después de provocar su despido de la empres Müeller, Judith está dispuesta a alejarse para siempre de Eric Zimmerman. Para ello y para reconducir su vida decide refugiarse en casa de su padre, en Jerez. Atormentado por su marcha, Eric le sigue el rastro. ', '2024-08-08 00:54:18', 545554, 4, 1, 4, 2013, 'miniaturas\\pideme.jpg', 'pdfs\\Pídeme lo que quieras, ahora y siempre ( PDFDrive ).pdf'),
(94, 'El retrato', 'Nikolái Gógol', 'El retrato es una novela corta escrita por Nikolai Gógol. Escribió una primera versión en 1835 y una segunda versión en 1842.', '2024-08-08 01:16:35', 545554, 10, 1, 14, 1835, 'miniaturas\\elretrato.jpg', 'pdfs\\Retrato.pdf'),
(95, 'La avenida Nevski', 'Nikolái Gógol', 'La avenida Nevski o La avenida del Nevá es un cuento de Nikolái Gógol escrito entre 1831 y 1834, y publicado en 1835. ', '2024-08-08 01:25:38', 1096063951, 7, 12, 15, 1835, 'miniaturas\\persspectiva.jpg', 'pdfs\\PerspectivaNevsky.pdf'),
(96, 'El encuentro inesperado', 'Johann Peter Hebel', 'Un minero de Falun (Suecia) y su amada quieren casarse pero pocos días antes de la ceremonia el minero se va a la mina pero nunca regresa. Pasan 50 años, de los cuales el autor narra los eventos históricos. Luego encuentran en Falun una mina hundida en la que hay un cadáver intacto.', '2024-08-08 01:28:13', 1096063951, 16, 5, 5, 1790, 'miniaturas\\encuentroinesperado.jpg', 'pdfs\\EncuentroInesperado.pdf'),
(97, 'CUENTOS DE MIEDO', 'E. T. A. Hoffmann', 'Esta edición contiene 5 cuentos de suspenso y terror gótico Hoffmann, E.T.A. donde se puede apreciar su ingenio para sorprender al lector', '2024-08-08 01:30:15', 1096063951, 3, 1, 2, 2019, 'miniaturas\\cuentosdemiedo.jpg', 'pdfs\\CuentosDeMiedo_Hoffmann.pdf'),
(98, 'El hombre que calculaba', 'Malba Tahan', 'Este libro narra las aventuras de Beremiz Samir, un calculista árabe que utiliza sus habilidades matemáticas para resolver diversos problemas y desafíos que encuentra en su camino.', '2024-08-08 01:45:57', 13814, 1, 12, 3, 1938, 'miniaturas\\elhombrequecalculaba.jpg', 'pdfs\\Retrato.pdf'),
(99, 'Breve historia del tiempo', 'Stephen Hawking', 'Hawking explica conceptos complejos de la cosmología como el Big Bang, los agujeros negros y la teoría de cuerdas, haciendo accesible la física moderna al público general.', '2024-08-08 01:47:02', 13814, 8, 2, 2, 1988, 'miniaturas\\historiadeltiempo.jpg', 'pdfs\\EncuentroInesperado.pdf'),
(100, 'El origen de las especies', 'Charles Darwin', 'Este libro revolucionario introduce la teoría de la evolución por selección natural, explicando cómo las especies cambian con el tiempo en respuesta a su entorno.', '2024-08-08 01:48:38', 13814, 9, 4, 14, 1859, 'miniaturas\\origenespecies.jpg', 'pdfs\\PerspectivaNevsky.pdf'),
(101, 'Álgebra de Baldor', 'Aurelio Baldor', 'Álgebra​ es un libro del matemático y profesor cubano Aurelio Baldor. La primera edición se produjo el 19 de junio de 1941. El Álgebra de Baldor contiene un total de 5790 ejercicios, que equivalen a 19 ejercicios en cada prueba en promedio.Décadas atrás se considera uno de las máximas producciones algebraicas.', '2024-08-08 01:52:45', 13814, 1, 1, 14, 1941, 'miniaturas\\algebrabaldor.jpg', 'pdfs\\algebrabaldor.pdf'),
(103, 'ADSO SENA', 'abel', 'En mi experiencia, las tecnologías digitales han potenciado la motivación y el compromiso de los estudiantes. Sin embargo, uno de los principales desafíos es la gestión del tiempo, ya que la preparación y adaptación de materiales digitales requieren más tiempo y esfuerzo. Además, es crucial encontrar un equilibrio para', '2024-08-08 17:56:22', 545554, 4, 5, 15, 1835, 'miniaturas\\reyes.png', 'pdfs\\CartaEncontro.pdf');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_estado_usua`
--

CREATE TABLE `tb_estado_usua` (
  `id_estado` int(11) NOT NULL,
  `estado` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_estado_usua`
--

INSERT INTO `tb_estado_usua` (`id_estado`, `estado`) VALUES
(0, 'Inhabilitado'),
(1, 'Habilitado');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_foro`
--

CREATE TABLE `tb_foro` (
  `id_foro` int(11) NOT NULL,
  `tit_foro` varchar(100) DEFAULT NULL,
  `descrip_foro` text DEFAULT NULL,
  `fecha_creacion` datetime NOT NULL,
  `id_asig_fk` int(11) DEFAULT NULL,
  `doc_usua_fk` int(11) DEFAULT NULL,
  `id_idioma_fk` int(11) DEFAULT NULL,
  `id_tpfr_fk` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_foro`
--

INSERT INTO `tb_foro` (`id_foro`, `tit_foro`, `descrip_foro`, `fecha_creacion`, `id_asig_fk`, `doc_usua_fk`, `id_idioma_fk`, `id_tpfr_fk`) VALUES
(21, 'Pena de muerte: Respuestas a tus preguntas', '<h1>Pena de muerte: Respuestas a tus preguntas</h1><h2>¿Previene la pena de muerte la delincuencia? ¿Ofrece justicia a las víctimas? ¿Existe una forma humana de ejecutar? Conoce toda la información sobre la pena de muerte a través de las 10 preguntas más frecuentes de Amnistía sobre la pena capital.</h2><h3>1. ¿Por qué se opone Amnistía Internacional a la pena de muerte?</h3><p>La pena de muerte viola&nbsp;<a href=\"https://www.amnesty.org/es/what-we-do/death-penalty/\" target=\"_blank\" style=\"color: rgb(0, 0, 0); background-color: transparent;\"><strong>el derecho más fundamental, el derecho a la vida. Es la forma más extrema de pena cruel, inhumana y degradante</strong></a>.</p><p>La pena de muerte se aplica de forma discriminatoria. Se usa con frecuencia contra las personas más vulnerables de la sociedad, incluidas las minorías étnicas y religiosas, los pobres, y las personas con discapacidad psíquica. Algunos gobiernos la utilizan para silenciar a sus oponentes. Cuando los sistemas de justicia tienen deficiencias y los juicios injustos están generalizados, existe siempre el riesgo de ejecutar a una persona inocente.</p><p>Cuando se aplica la pena de muerte, es irreversible. Los errores cometidos no se pueden deshacer. Una persona inocente puede ser liberada de la prisión por un delito que no cometió, pero&nbsp;<a href=\"https://www.amnesty.org/en/latest/news/2011/09/us-executes-death-row-prisoner-troy-davis/\" target=\"_blank\" style=\"color: rgb(0, 0, 0); background-color: transparent;\"><strong>una ejecución nunca se puede revertir</strong></a>.</p><h3>2. ¿No tienen las víctimas de delitos violentos y sus familias derecho a justicia?</h3><p>Sí lo tienen. Las personas que han perdido a seres queridos en crímenes terribles tienen derecho a ver a la persona responsable rendir cuentas en un juicio justo sin recurso a la pena de muerte. Al oponernos a la pena de muerte, no estamos intentando minimizar o aceptar la delincuencia, pero, como han dicho muchas familias que han perdido a seres queridos, la pena de muerte no puede verdaderamente aliviar su sufrimiento. Simplemente extiende ese sufrimiento a la familia de la persona condenada.</p><p><img src=\"https://ichef.bbci.co.uk/ace/ws/640/cpsprodpb/37C8/production/_97808241_gettyimages-645595980.jpg.webp\" alt=\"Guatemala, pena de muerte\"></p>', '2024-07-13 21:26:32', 3, 545554, 1, 4),
(22, 'Afganistán: Un nuevo informe de la ONU ', '<h1>Subraya la necesidad urgente de que los talibanes den marcha atrás al control policial de la moral</h1><p><img src=\"https://www.es.amnesty.org/fileadmin/user_upload/GettyImages-2014980031.jpg\" height=\"683\" width=\"1024\"></p><p>© KARIM JAAFAR/AFP via Getty Images</p><p><span style=\"color: rgb(89, 89, 89);\">10 de julio de 2024</span></p><p>En respuesta a un&nbsp;<a href=\"https://unama.unmissions.org/sites/default/files/moral_oversight_report_english_final.pdf\" target=\"_blank\" style=\"color: rgb(0, 0, 0);\">nuevo informe</a>&nbsp;de la Misión de Asistencia de las Naciones Unidas en Afganistán (UNAMA) sobre las violaciones graves y generalizadas de derechos humanos cometidas por el denominado Ministerio para la Propagación de la Virtud y la Prevención del Vicio, un ministerio talibán de facto, Zaman Sultani, investigador regional de Amnistía Internacional para Asia Meridional, ha declarado:</p>', '2024-07-13 22:00:26', 3, 545554, 1, 2),
(25, 'TAREAS PENDIENTES POR TERMINAR PROYECTO B_VIRTUAL', '<p><strong><em><u>- ADMIN GESTIONAR DOCENTES</u></em></strong></p><p><strong><em><u>- VALIDAR SALIDAS DE QUE SI NO TIENE SESION ME MANDE AL INDEX</u></em></strong></p><p><strong><em><u>- GESTIONAR ESTUDIANTES</u></em></strong></p><p><strong><em><u>- ESTUDIANTES ACTUALIZAR O CAMBIAR SUS DATOS</u></em></strong></p><p><strong><em><u>- LIMPIAR LA BASE DE DATOS&nbsp;</u></em></strong></p><p><strong><em><u>- SUPERADMIN GESTIONAR ADMIN, DOCENTES Y ESTUDIANTES</u></em></strong></p><p><strong><em><u>- TERMINAR LA PARTE DE LIBROS Y DE FOROS (CORREGIR CLASES DAO)</u></em></strong></p><p><strong><em><u>- PERFECCIONAR LOGIN Y REGISTRO</u></em></strong></p><p><strong><em><u>- ASEGURAR PAGINAS, QUE SOLO ENTRE EL ROL ESPECIFICO Y QUE NO SE DEVULA DESPUES DEL LOGIN Y ESO, DESPUES DE CERRAR SESION</u></em></strong></p><p><strong><em><u>- BARRA DE BUSQUEDA</u></em></strong></p><p><strong><em><u>- ENCRIPTAR CONTRASEÑAS</u></em></strong></p><p>- PAGINACIONES</p><p>- VERIFICAR RESPONSIVES</p><p><strong><em><u>- EDITAR TITULO EN FOROS</u></em></strong></p><p><strong><em><u>- EDITAR LIBROS</u></em></strong></p><p><strong><em><u>- SE PUEDEN CONVERTIR PROFESORES A ADMINISTRADORES</u></em></strong></p><p><strong><em><u>- CONFIRMAR CONTRASEÑA Y EDITAR DATOS DE DOCENTE Y ADMINISTRADOR</u></em></strong></p><p>- revisar la parte de id del usuario pasar a long</p><p><strong><em><u>- CAMBIAR LO DEL PDF (SI ALCANZO)</u></em></strong></p><p><strong><em><u>- CREAR EDITAR CATEGORIAS, IDIOMAS, ETC</u></em></strong></p><p><strong><em><u>- HACER MAS PAGINAS</u></em></strong></p><p><strong><em><u>- ACOMODAR GESTIONAR</u></em></strong></p><p><br></p><p><br></p><p>OPCIONAL: HACER LAS VALIDACIONES CON JS EN LOS CRUD DE USUARIOS</p>', '2024-07-16 01:55:50', 4, 545554, 1, 5),
(35, 'Pena de Muerte', '<h3><strong>Introducción</strong></h3><p>La pena de muerte es uno de los temas más controvertidos en el ámbito del derecho penal. Este foro tiene como objetivo analizar diversos aspectos de la pena capital, su aplicación, y las implicaciones éticas y legales que conlleva.</p><p><br></p><h3><strong>Objetivos del Foro</strong></h3><ol><li><strong>Investigar</strong> y <strong>discutir</strong> los fundamentos legales y morales de la pena de muerte.</li><li>Evaluar la efectividad de la pena de muerte como <strong>método de disuasión</strong>.</li><li><strong>Comparar</strong> la aplicación de la pena de muerte en diferentes países.</li><li>Analizar casos emblemáticos y <strong>jurisprudencia</strong> relevante.</li></ol><p><br></p><h3><strong>Preguntas para la Discusión</strong></h3><ol><li><strong>¿Cuál es el fundamento legal de la pena de muerte en tu país?</strong></li><li><strong>¿La pena de muerte es un disuasivo eficaz contra el crimen?</strong></li><li><strong>¿Qué argumentos éticos se utilizan para defender o condenar la pena de muerte?</strong></li><li><strong>Comparación internacional</strong></li><li><strong>Casos emblemáticos</strong></li></ol><p><br></p><h3><strong>Recursos Recomendados</strong></h3><ul><li>Amnistía Internacional: Informe sobre la Pena de Muerte</li><li>Human Rights Watch: La Pena de Muerte</li><li><strong>Libros:</strong></li><li class=\"ql-indent-1\"><em>\"Contra la pena de muerte\"</em> de <strong>Mario Marazziti</strong></li><li class=\"ql-indent-1\"><em>\"La pena de muerte en el siglo XXI\"</em> de <strong>Raúl Zaffaroni</strong></li></ul><p><br></p><h3><strong>Criterios de Participación</strong></h3><ul><li><strong>Subraya</strong> tus puntos clave y <strong>resalta en negrita</strong> las ideas principales.</li><li>Utiliza <strong>citas</strong> y <em>referencias</em> para apoyar tus argumentos.</li><li>Participa <strong>respetuosamente</strong> y <strong>escucha</strong> las opiniones de tus compañeros.</li></ul><p><br></p><p><img src=\"https://i.pinimg.com/236x/d5/39/17/d539174e175e07e8a374616766a44750.jpg\" alt=\"Las mejores 8 ideas de Mejores imagenes del mundo | mejores imagenes del  mundo, hermosa fotografía de paisaje, fotografía de naturaleza\"></p>', '2024-08-02 00:58:02', 3, 545554, 1, 2),
(36, '225 preguntas de cultura general y sus respuestas', '<h2>Una selección de preguntas de cultura general para poner a prueba tus conocimientos.</h2><p>Las preguntas de cultura general suelen formularse en exámenes altamente competitivos&nbsp;en los que se pretende realizar una criba de participantes (por ejemplo, en oposiciones). Generalmente, se realiza este examen junto a distintos tests que pretenden medir&nbsp;<a href=\"https://psicologiaymente.com/inteligencia/tipos-de-inteligencia\" target=\"_blank\" style=\"background-color: rgb(255, 255, 255); color: rgb(73, 41, 188);\">otros tipos de inteligencia</a>: psicotécnicos de razonamiento verbal, psicotécnicos de razonamiento numérico o psicotécnicos de razonamiento lógico.</p><p><strong><em><u>Estas pruebas suelen tener límite de tiempo para realizarlos, y los resultados suelen sumarse para determinar si el participante es apto o no para seguir dentro del proceso de selección de un trabajo o una plaza de estudio.</u></em></strong></p><p><br></p><h2>Preguntas de cultura general y sus respuestas</h2><p>Si estás preparándote para uno oposición o quieres acceder a una plaza para cursas tus estudios en un centro educativo,&nbsp;a continuación puedes encontrar una larga lista de preguntas de cultura general y sus respuestas.</p><h3>1. ¿Cuáles son los cinco tipos de sabores primarios?</h3><p>El gusto es uno de nuestros cinco sentidos. Los sabores primarios son dulce, amargo, ácido, salado y umami.</p><ul><li>Artículo relacionado: “<a href=\"https://psicologiaymente.com/neurociencias/neurogastronomia-comer-cerebro\" target=\"_blank\" style=\"background-color: rgb(255, 255, 255); color: rgb(73, 41, 188);\">Neurogastronomía: comer con el paladar, un acto del cerebro</a>”</li></ul><h3>2. ¿Cuál es el lugar más frío de la tierra?</h3><p>La Antártida, que está cubierta de capas con un espesor de entre dos mil y tres mil metros</p>', '2024-08-03 14:02:56', 2, 545554, 1, 4),
(37, 'Temas de conversación divertidos para hablar con familiares', '<p>Encontrar temas para hablar con alguien de la familia&nbsp;<strong>no es tan complicado, el nivel de intimidad, y el tiempo que pasamos junto a nuestros familiares nos asegura tener muchas cosas en común</strong>&nbsp;sobre las que se puede hablar.</p><p>Aún así, es probable que a causa de las ocupaciones o las distracciones no compartamos con nuestra familia tanto como nos gustaría.</p><p>Existen muchos temas de conversación divertidos que&nbsp;<strong>te pueden ayudar para reforzar tus lazos familiares</strong>, y pasar más de una tarde agradable con quienes están contigo en el día a día.</p><p>Aquí te dejamos una lista de temas de conversación interesantes para tener con tu familia,&nbsp;<strong>cualquiera de ellos te puede servir para pasar&nbsp;un buen rato con las personas que más quieres</strong>.</p><p>1. Historias y anécdotas familiares.</p><p>2. Planes sobre el futuro.</p><p>3. Gustos sobre la cocina.</p><p>4. Fiestas y fechas especiales.</p><p>5. Inteligencia emocional.</p><p>6. Sueños y objetivos personales.</p><p>7. Miedos.</p><p>8. Temas de actualidad.</p><p>9. Hobbies.</p><p>10. Emociones.</p><p>Con estos temas para conversar y algunas&nbsp;<a href=\"https://www.crehana.com/blog/empleabilidad/tecnicas-de-comunicacion-asertiva/\" target=\"_blank\" style=\"color: var(--color-primary-main,#4B22F4); background-color: transparent;\"><strong>técnicas de comunicación asertiva</strong></a>, la relación con tus seres queridos será cada vez más estrecha.</p><p><img src=\"https://crehana-blog.imgix.net/media/filer_public/7e/9a/7e9a8ea7-72c2-4b04-9393-db6559966b91/temas-conversacion-divertidos-familia.jpg?auto=format&amp;q=50\" alt=\"Temas para hablar en familia\"></p><p><br></p>', '2024-08-06 21:41:40', 2, 22, 10, 3),
(40, 'Dia Internacional Abolición de la Pena de Muerte', '<p><a href=\"https://www.cancilleria.gov.co/sites/default/files/pena%20de%20muerte.jpg\" target=\"_blank\" style=\"color: rgb(51, 102, 204); background-color: transparent;\"><img src=\"https://www.cancilleria.gov.co/sites/default/files/styles/920x400/public/pena%20de%20muerte.jpg?h=1a007bb6&amp;itok=fmdDO0hr\" alt=\"pena de muerte\" height=\"420\" width=\"920\"></a></p><p>Nella Giornata internazionale contro la pena di morte, la Colombia esorta gli Stati che la mantengono a istituire una moratoria sulle esecuzioni con l\'obiettivo di abolirla.</p><p><br></p><p>La Colombia ha vietato questa pratica con una norma di rango costituzionale – articolo 11 della Costituzione Nazionale del 1991: “Il diritto alla vita è inviolabile. Non ci sarà alcuna pena di morte.”- e ha riaffermato questo impegno con la ratifica del Protocollo alla Convenzione americana sui diritti umani relativa all’abolizione della pena di morte nel 2010.</p><p><br></p><p>Il governo del presidente Gustavo Petro resta impegnato a favore della vita e sollecita gli Stati che ancora mantengono la pena di morte a istituire una moratoria sulle esecuzioni con l\'obiettivo di abolirla.</p>', '2024-08-07 14:21:58', 4, 1567894, 12, 4),
(43, 'La Unificación de Italia', '<p><strong>Marco Rossi:</strong></p><p>La Unificación de Italia es uno de los eventos más importantes en la historia europea del siglo XIX. Sin embargo, me gustaría entender mejor los factores principales que llevaron a la unificación. ¿Cuáles fueron los eventos clave y las figuras más influyentes en este proceso? Agradecería cualquier información detallada sobre este tema.</p>', '2024-08-08 02:09:09', 4, 11, 1, 4),
(45, 'Fonds européen de développement régional', '<p>La langue française est une langue romane parlée en France, à l\'origine de cette langue, ainsi qu\'au Canada, en Belgique, au Luxembourg, en Suisse et dans cinquante et un autres pays dont la plupart faisaient partie de l\'ancien empire colonial français, notamment en Afrique. , ainsi que la République Démocratique du Congo, ancien Congo Belge.</p><p><br></p><p>Le français, langue royale, évolue du bas latin et du latin vulgaire au gallo-roman au cours du premier millénaire de l\'ère chrétienne et devient une langue juridique et administrative avec l\'ordonnance de Villers-Cotterêts en 1539. Dès lors, le français, toujours présent le noyau parisien, se répandra en France, en Europe et dans le monde.1 S\'ensuit une longue réforme de la langue, promue par les universitaires, pour la régulariser et réintroduire les termes latins. Le français classique des XVIe et XVIIe siècles est devenu le français moderne du XVIIIe siècle, la lingua franca de l\'Europe. Avec la colonisation française, le français s\'est répandu en Amérique du Nord au XVIIe siècle et en Afrique au XIXe siècle, devenant ainsi une lingua franca. Cependant, le français a perdu de son influence dans la seconde moitié du XXe siècle au profit de l\'anglais.</p><p><br></p><p>Claude Hagège distingue trois périodes d\'influence française : le Moyen Âge, de la fin du XIe siècle au début du XIVe siècle ; la période allant du début du règne de Louis XIV à la fin du XVIIIe siècle ; et la période allant de la fin du XIXe siècle au début du XXe siècle.2</p>', '2024-08-08 02:57:12', 4, 87654321, 4, 7),
(48, 'PROYECTOS FINALES', '<p>¿Cuales fueron sus expectativitas frente a la sustentación de sus proyectos finales de su etapa de formación?</p><p>Hoy</p>', '2024-08-08 17:52:45', 7, 45678328, 1, 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_idiomas`
--

CREATE TABLE `tb_idiomas` (
  `id_idioma` int(11) NOT NULL,
  `nom_idioma` varchar(30) NOT NULL,
  `estado` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_idiomas`
--

INSERT INTO `tb_idiomas` (`id_idioma`, `nom_idioma`, `estado`) VALUES
(1, 'Español', 1),
(2, 'Ingles', 1),
(4, 'Frances', 1),
(5, 'Aleman', 1),
(10, 'Griego', 1),
(12, 'Italiano', 1),
(13, 'Chino', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_respuesta_foro`
--

CREATE TABLE `tb_respuesta_foro` (
  `id_respu` int(11) NOT NULL,
  `contenido` text DEFAULT NULL,
  `fecha_public` datetime DEFAULT NULL,
  `id_foro_fk` int(11) DEFAULT NULL,
  `doc_usua_fk` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_respuesta_foro`
--

INSERT INTO `tb_respuesta_foro` (`id_respu`, `contenido`, `fecha_public`, `id_foro_fk`, `doc_usua_fk`) VALUES
(4, '<h1>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. </h1><p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. </strong></p>', '2024-07-14 01:01:25', 21, 11),
(5, '<h1>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. </h1><p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. </strong></p>', '2024-07-14 01:41:27', 21, 545554),
(17, '<p>En la Giornata internazionale contro la pena di morte, Colombia exhorta a los Estados que aún la mantienen a establecer una moratoria sobre las ejecuciones con el objetivo de abolirla. effef</p><p><br></p>', '2024-07-14 20:17:07', 21, 545554),
(23, '<h1>Obesidad y sobrepeso</h1><h2>Datos y cifras</h2><ul><li>En 2022, una de cada ocho personas en el mundo eran obesas.&nbsp;</li><li>Desde 1990, la obesidad se ha duplicado con creces entre los adultos de todo el mundo, y se ha cuatriplicado entre los adolescentes.</li><li>En 2022, 2500 millones de adultos (18 años o más) tenían sobrepeso. De ellos, 890 millones eran obesos.</li><li>En 2022, el 43% de los adultos de 18 años o más tenían sobrepeso, y el 16% eran obesos.</li><li>En 2022, 37 millones de niños menores de 5 años tenían sobrepeso.</li><li>En 2022, más de 390 millones de niños y adolescentes de 5 a 19 años tenían sobrepeso, de los cuales 160 millones eran obesos.      </li></ul>', '2024-07-14 23:44:49', 21, 10),
(24, '<h2>COSAS QUE PASAN BRO</h2><h2>Generalidades</h2><p>El sobrepeso es una afección que se caracteriza por una acumulación excesiva de grasa.</p><p>La obesidad es una compleja enfermedad crónica que se define por una acumulación excesiva de grasa que puede ser perjudicial para la salud. La obesidad puede provocar un aumento del riesgo de diabetes de tipo 2 y cardiopatías, puede afectar la salud ósea y la reproducción y aumenta el riesgo de que aparezcan determinados tipos de cáncer. La obesidad influye en aspectos de la calidad de vida como el sueño o el movimiento.</p><p>El diagnóstico del sobrepeso y la obesidad se efectúa midiendo el peso y la estatura de las personas y calculando el índice de masa corporal (IMC): peso (kg)/estatura<sup>2</sup>&nbsp;(m<sup>2</sup>). Este índice es un marcador indirecto de la grasa, y existen mediciones adicionales, como el perímetro de la cintura, que pueden ayudar a diagnosticar la obesidad.</p><p>Las categorías del IMC para definir la obesidad varían, en función de la edad y el género, para lactantes, niños y adolescentes.</p>', '2024-07-14 23:45:10', 21, 10),
(37, '<p><br></p><h2>Generalidades</h2><p>El sobrepeso es una afección que se caracteriza por una acumulación excesiva de grasa.</p><p>La obesidad es una compleja enfermedad crónica que se define por una acumulación excesiva de grasa que puede ser perjudicial para la salud. La obesidad puede provocar un aumento del riesgo de diabetes de tipo 2 y cardiopatías, puede afectar la salud ósea y la reproducción y aumenta el riesgo de que aparezcan determinados tipos de cáncer. La obesidad influye en aspectos de la calidad de vida como el sueño o el movimiento.</p><p>El diagnóstico del sobrepeso y la obesidad se efectúa midiendo el peso y la estatura de las personas y calculando el índice de masa corporal (IMC): peso (kg)/estatura<sup>2</sup>&nbsp;(m<sup>2</sup>). Este índice es un marcador indirecto de la grasa, y existen mediciones adicionales, como el perímetro de la cintura, que pueden ayudar a diagnosticar la obesidad.</p><p>Las categorías del IMC para definir la obesidad varían, en función de la edad y el género, para lactantes, niños y adolescentes.</p><h3>Población adulta</h3><p>En el caso de los adultos, la OMS define el sobrepeso y la obesidad así:</p><p><br></p><ul><li>sobrepeso: IMC igual o superior a 25; y</li><li>obesidad: IMC igual o superior a 30.</li></ul><p>En el caso de los niños, debe tenerse en cuenta la edad al definir el sobrepeso y la obesidad.</p><h3>Niños menores de 5 años</h3><p>En el caso de los niños menores de 5 años:</p><p><br></p><ul><li>el sobrepeso es un peso para la estatura superior a dos desviaciones típicas por encima de la mediana de los patrones de crecimiento infantil de la OMS; y</li><li>la obesidad es un peso para la estatura superior a tres desviaciones típicas por encima de la mediana de los patrones de crecimiento infantil de la OMS.&nbsp;</li></ul><p><a href=\"https://www.who.int/tools/child-growth-standards\" target=\"_blank\" style=\"color: var(--l3-neutralForegroundRest);\">Gráficos y tablas: patrones de crecimiento infantil de la OMS para niños menores de 5 años</a>&nbsp;(en inglés)</p><h3>Niños de 5 a 19 años</h3><p>El sobrepeso y la obesidad se definen de la siguiente manera para los niños de 5 a 19 años:</p><p><br></p><ul><li>el sobrepeso es un IMC para la edad superior a una desviación típica por encima de la mediana de la referencia de crecimiento de la OMS; y</li><li>la obesidad es un IMC para la edad superior a dos desviaciones típicas por encima de la mediana de la referencia de crecimiento de la OMS.</li></ul><p><a href=\"https://www.who.int/tools/growth-reference-data-for-5to19-years\" target=\"_blank\" style=\"color: var(--l3-neutralForegroundRest);\">Gráficos y tablas: patrones de crecimiento de la OMS para niños de 5 a 19 años</a>&nbsp;(en inglés)</p><h2>Datos sobre el sobrepeso y la obesidad</h2><p>En 2022, 2500 millones de adultos de 18 o más años tenían sobrepeso, de los cuales más de 890 millones eran obesos. Esto significa que el 43% de los adultos de 18 años o más (un 43% de hombres y un 44% de mujeres) tenían sobrepeso, lo que supone un aumento con respecto a 1990, cuando el porcentaje de adultos de 18 años o más con sobrepeso era del 25%. La prevalencia del sobrepeso variaba en función de la región: del 31% en las regiones de la OMS de Asia Sudoriental y África al 67% en la Región de las Américas.</p><p>En 2022, alrededor del 16% de los adultos de 18 años o más en todo el mundo eran obesos. La prevalencia de la obesidad en todo el mundo aumentó en más del 100%&nbsp;entre 1990 y 2022.</p><p>Se estima que, en 2022, 37 millones de niños menores de 5 años tenían sobrepeso. Considerado en tiempos como un problema de los países de ingreso alto, el sobrepeso va en aumento en los países de ingreso bajo y mediano. En África, el número de niños menores de 5 años con sobrepeso se ha incrementado en casi un 23% desde el año 2000. Prácticamente la mitad de los niños menores de 5 años que tenían sobrepeso o eran obesos en 2022 vivían en Asia.</p><p>En 2022, más de 390 millones de niños y adolescentes de 5 a 19 años tenían sobrepeso. La prevalencia del sobrepeso (lo que incluye la obesidad) entre los niños y adolescentes de 5 a 19 años ha aumentado drásticamente, pasando del 8% en 1990 al 20% en 2022. Este aumento ha afectado de la misma manera a niños y niñas: en 2022, el 19% de las niñas y el 21% de los niños tenían sobrepeso.</p><p>Mientras que tan solo el 2% de los niños y adolescentes de 5 a 19 años eran obesos en 1990 (31 millones de jóvenes), en 2022 el 8% de los niños y adolescentes eran obesos (160 millones de jóvenes).</p><h2>Causas del sobrepeso y la obesidad</h2><p>El sobrepeso y la obesidad son la consecuencia de un desequilibrio entre la ingesta calórica (alimentación) y el gasto calórico (actividad física).</p><p>En la mayoría de los casos, la obesidad es una enfermedad multifactorial que se debe a un entorno obesogénico, factores psicosociales y variantes genéticas. En un subgrupo de pacientes, se pueden identificar los principales factores etiológicos individuales (medicamentos, enfermedades, falta de movimiento, procedimientos iatrogénicos, o enfermedad monogénica/síndrome genético).</p><p>El entorno obesogénico que exacerba la probabilidad de la obesidad en individuos, poblaciones y en diferentes entornos está relacionado con factores estructurales que limitan la disponibilidad de alimentos saludables y sostenibles a precios asequibles a nivel local, la falta de situaciones en la vida cotidiana de la población en las que sea fácil y seguro realizar una cierta actividad física y la ausencia de un entorno legal y reglamentario adecuado.</p><p>Al mismo tiempo, la falta de una respuesta eficaz del sistema de salud para identificar el exceso de aumento de peso y de depósitos de grasa durante las primeras etapas agrava la aparición de la obesidad.</p><h2>Consecuencias comunes para la salud</h2><p>VAMOS A VER ENTONCES</p><p>Los riesgos del sobrepeso y la obesidad para la salud están cada vez mejor documentados y estudiados.</p><p>Se estima que, en 2019, un IMC superior al óptimo provocó cinco millones de muertes por enfermedades no transmisibles (ENT) como las enfermedades cardiovasculares, la diabetes, el cáncer, los trastornos neurológicos, las enfermedades respiratorias crónicas o los trastornos digestivos&nbsp;<em>(1)</em>.&nbsp;</p><p>El sobrepeso en la infancia y la adolescencia afecta de forma inmediata a la salud de los niños y adolescentes, y está asociado a un mayor riesgo de contraer, de manera precoz, distintas ENT, como la diabetes de tipo 2 y las enfermedades cardiovasculares.&nbsp;<a href=\"https://www.who.int/es/publications/i/item/WHO-NMH-NHD-14.2\" target=\"_blank\" style=\"color: var(--l3-neutralForegroundRest);\">La obesidad en la infancia y la adolescencia</a>&nbsp;tiene consecuencias psicosociales adversas; afecta al rendimiento escolar y a la calidad de vida, a lo que se añaden la estigmatización, la discriminación y la intimidación. Los niños con obesidad tienen muchas probabilidades de ser adultos con obesidad y además corren un mayor riesgo de sufrir ENT en la edad adulta.</p><p>Las consecuencias económicas de la epidemia de obesidad también son importantes. Se calcula que, si no se toman medidas al respecto, los costos a escala mundial del sobrepeso y la obesidad alcanzarán los US$ 3 billones anuales para 2030 y una cifra superior a los US$ 18 billones para 2060&nbsp;<em>(2)</em>.</p><p>Por último, el aumento de las tasas de obesidad en los países de ingreso bajo y mediano, en particular entre los grupos socioeconómicos más bajos, está globalizando rápidamente un problema que antes se asociaba únicamente a los países de ingreso alto.          </p>', '2024-07-15 00:57:30', 21, 10),
(39, '<h2>Progreso en mi rutina de entrenamiento</h2>\n    <h3>Resultados</h3>\n    <p>Es increíble ver los progresos que he logrado en los últimos meses. La consistencia y el esfuerzo realmente dan frutos.</p>\n    <p>He estado siguiendo una rutina de entrenamiento estricta y una dieta balanceada, y los resultados son evidentes.</p>\n    <p>Si alguien está buscando consejos sobre cómo empezar, estaré encantado de compartir más detalles sobre mi régimen.</p>', '2024-07-15 02:08:20', 21, 10),
(40, '<h2>Receta de lasaña casera</h2>\n    <h3>Ingredientes y preparación</h3>\n    <p>Gracias por la receta de lasaña. La probé anoche y fue un éxito rotundo en mi casa.</p>\n    <p>La combinación de sabores es perfecta y la preparación fue más sencilla de lo que esperaba.</p>\n    <p>Si tienes más recetas de este estilo, me encantaría probarlas. ¡Siempre estoy buscando nuevas ideas para la cocina!</p>', '2024-07-15 02:17:55', 21, 10),
(43, '<h2>Destinos menos conocidos en Europa</h2>\n    <h3>Experiencias</h3>\n    <p>Viajar a destinos menos conocidos puede ser una experiencia mucho más enriquecedora que visitar los lugares turísticos tradicionales.</p>\n    <p>Recientemente, visité un pequeño pueblo en Italia que fue una verdadera joya escondida. La cultura local y la hospitalidad de la gente hicieron que el viaje fuera inolvidable.</p>\n    <p>Recomiendo encarecidamente explorar estos destinos para quienes buscan una experiencia auténtica y única.</p>\n</div>', '2024-07-15 16:45:01', 21, 10),
(47, '<h2>Reseña del libro \"El Alquimista\"</h2>\n    <h3>Opinión</h3>\n    <p>Acabo de terminar de leer \"El Alquimista\" de Paulo Coelho y quedé profundamente impresionado.</p>\n    <p>La narrativa es cautivadora y el desarrollo de los personajes es realmente profundo. La trama te mantiene enganchado de principio a fin.</p>\n    <p>Es un libro que recomiendo a todos los amantes de la literatura y aquellos que buscan una historia inspiradora.</p>', '2024-07-15 18:13:48', 21, 10),
(67, '<p>La postura de Colombia es un reflejo de un movimiento global hacia la abolición de la pena de muerte. Históricamente, muchos países han utilizado la pena de muerte como un medio para mantener el orden y castigar los delitos graves. Sin embargo, en las últimas décadas, ha habido un cambio significativo en la percepción de este castigo, visto cada vez más como una violación de los derechos humanos fundamentales. Es alentador ver a países como Colombia liderando el camino hacia un mundo sin pena de muerte.</p><p><br></p>', '2024-07-15 21:26:33', 21, 10),
(86, '<h2>Receta de lasaña casera</h2>\n    <h3>Ingredientes y preparación</h3>\n    <p>Gracias por la receta de lasaña. La probé anoche y fue un éxito rotundo en mi casa.</p>\n    <p>La combinación de sabores es perfecta y la preparación fue más sencilla de lo que esperaba.</p>\n    <p>Si tienes más recetas de este estilo, me encantaría probarlas. ¡Siempre estoy buscando nuevas ideas para la cocina!</p>', '2024-07-22 14:59:55', 22, 13814),
(93, '<h2>Métodos efectivos de estudio</h2>\n    <h3>Sugerencias</h3>\n    <p>He estado experimentando con diferentes métodos de estudio y he encontrado que la técnica Pomodoro es bastante efectiva.</p>\n    <p>Dividir el tiempo de estudio en intervalos de 25 minutos con descansos cortos ayuda a mantener la concentración.</p>\n    <p>¿Qué métodos de estudio han funcionado mejor para ustedes?</p>', '2024-07-27 23:11:08', 25, 545554),
(95, '<h2>Métodos efectivos de estudio</h2>\n    <h3>Sugerencias</h3>\n    <p>He estado experimentando con diferentes métodos de estudio y he encontrado que la técnica Pomodoro es bastante efectiva.</p>\n    <p>Dividir el tiempo de estudio en intervalos de 25 minutos con descansos cortos ayuda a mantener la concentración.</p>\n    <p>¿Qué métodos de estudio han funcionado mejor para ustedes?</p>', '2024-07-27 23:12:37', 25, 545554),
(99, '<h2>Experiencia en \"The Witcher 3\"</h2>\n    <h3>Jugabilidad</h3>\n    <p>Recientemente empecé a jugar \"The Witcher 3\" y estoy impresionado por la calidad del juego.</p>\n    <p>La historia es envolvente y los gráficos son espectaculares. Me he perdido en este mundo durante horas.</p>\n    <p>¿Alguien tiene consejos sobre las mejores estrategias o misiones secundarias que no debo perderme?</p>', '2024-07-27 23:23:02', 25, 545554),
(100, '<h2>Estrategias para manejar el estrés</h2>\n    <h3>Consejos</h3>\n    <p>El estrés es algo con lo que todos lidiamos en algún momento. He encontrado que la meditación y el ejercicio regular son formas efectivas de manejar el estrés.</p>\n    <p>Además, hablar con amigos y familiares puede proporcionar un apoyo valioso.</p>\n    <p>¿Qué otras estrategias usan ustedes para manejar el estrés diario?</p>', '2024-07-27 23:31:07', 25, 545554),
(101, '<h2>Consejos para ahorrar dinero</h2>\n    <h3>Estrategias</h3>\n    <p>Ahorrar dinero puede ser difícil, pero he encontrado algunas estrategias que me han ayudado a mejorar mis finanzas personales.</p>\n    <p>Crear un presupuesto y seguirlo estrictamente, así como automatizar los ahorros, han sido métodos efectivos para mí.</p>\n    <p>¿Qué otros consejos tienen para ahorrar dinero de manera efectiva?</p>', '2024-07-27 23:36:40', 25, 545554),
(105, '<h2>Estrategias para manejar el estrés</h2>\n    <h3>Consejos</h3>\n    <p>El estrés es algo con lo que todos lidiamos en algún momento. He encontrado que la meditación y el ejercicio regular son formas efectivas de manejar el estrés.</p>\n    <p>Además, hablar con amigos y familiares puede proporcionar un apoyo valioso.</p>\n    <p>¿Qué otras estrategias usan ustedes para manejar el estrés diario?</p>', '2024-07-27 23:36:51', 25, 545554),
(107, '<h2>Ideas para decorar espacios pequeños</h2>\n    <h3>Inspiración</h3>\n    <p>Vivir en un espacio pequeño puede ser un desafío cuando se trata de decoración. He descubierto que usar muebles multifuncionales y colores claros puede hacer que el espacio parezca más grande.</p>\n    <p>Además, añadir espejos puede ayudar a reflejar la luz y crear una sensación de amplitud.</p>\n    <p>¿Qué otras ideas de decoración tienen para espacios pequeños?</p>', '2024-07-27 23:41:16', 25, 545554),
(121, '<h2>Cuidado de mascotas exóticas</h2>\n    <h3>Información</h3>\n    <p>Recientemente, adquirí una iguana como mascota y estoy buscando consejos sobre su cuidado.</p>\n    <p>He leído que necesitan un ambiente cálido y húmedo, así como una dieta rica en vegetales. Sin embargo, me gustaría saber más sobre sus necesidades específicas.</p>\n    <p>¿Alguien tiene experiencia con mascotas exóticas y puede ofrecer algunos consejos?</p>\n</div>', '2024-07-28 18:30:02', 25, 1),
(126, '<h2>Experiencia en \"The Witcher 3\"</h2>\n    <h3>Jugabilidad</h3>\n    <p>Recientemente empecé a jugar \"The Witcher 3\" y estoy impresionado por la calidad del juego.</p>\n    <p>La historia es envolvente y los gráficos son espectaculares. Me he perdido en este mundo durante horas.</p>\n    <p>¿Alguien tiene consejos sobre las mejores estrategias o misiones secundarias que no debo perderme?</p>', '2024-07-28 19:12:50', 25, 22),
(129, '<h3><code>private Conexion conexion;</code></h3><ul><li><strong>Mutable</strong>: Puedes reasignar una nueva instancia a <code>conexion</code> después de que ha sido inicialmente asignada.</li><li><strong>Uso típico</strong>: Esto es común cuando la instancia puede cambiar durante la vida útil del objeto que la contiene.</li></ul><p><br></p><h3><code>private final Conexion conexion;</code></h3><ul><li><strong>Inmutable</strong>: Una vez que <code>conexion</code> ha sido asignado, no puede ser reasignado a una nueva instancia. Sin embargo, esto no significa que el objeto al que apunta <code>conexion</code> sea inmutable; solo que la referencia a <code>conexion</code> no puede cambiar.</li><li><strong>Seguridad y claridad</strong>: Usar <code>final</code> comunica claramente que <code>conexion</code> no &lt;b&gt;cambiará&lt;/b&gt; después de ser asignado, lo que puede ayudar a prevenir errores accidentales y hacer el código más fácil de razonar.</li></ul><p><br></p>', '2024-07-31 00:50:24', 22, 11),
(131, '<h3>Fundamento Legal de la Pena de Muerte en México</h3><p>¡Hola a todos!</p><p>Quiero compartir con ustedes un poco sobre el fundamento legal de la pena de muerte en México. En 2005, nuestro país abolió formalmente la pena capital con la reforma del artículo 22 de la Constitución. Antes de esta reforma, la pena de muerte se aplicaba en casos muy específicos, como la traición a la patria durante la guerra. Ahora, cualquier forma de ejecución está prohibida, lo que refleja nuestro compromiso con los derechos humanos.</p><p><br></p>', '2024-08-02 01:02:35', 35, 545554),
(132, '<h3>Caso Emblemático: Troy Davis en Estados Unidos</h3><p>¡Hola a todos!</p><p>Quisiera hablar sobre el caso de <strong>Troy Davis</strong> en Estados Unidos, que realmente me impactó. Davis fue ejecutado en 2011 por el asesinato de un oficial de policía en 1989, aunque siempre sostuvo su inocencia. Lo más perturbador es que varios testigos se retractaron de sus declaraciones, creando serias dudas sobre su culpabilidad. A pesar de las protestas y peticiones de clemencia globales, Davis fue ejecutado, lo que generó un debate intenso sobre la justicia y la fiabilidad del sistema judicial.</p><p>¿Qué opinan sobre este caso? ¿Creen que existen suficientes salvaguardias para evitar errores judiciales en casos de pena de muerte?</p>', '2024-08-02 01:03:11', 35, 545554),
(138, '<h2>Consejos para cultivar tomates</h2>\n    <h3>Recomendaciones</h3>\n    <p>He estado cultivando tomates en mi jardín y he encontrado algunas técnicas útiles para maximizar el rendimiento.</p>\n    <p>Es importante regar las plantas regularmente y asegurarse de que reciban suficiente luz solar. Además, usar fertilizantes orgánicos puede mejorar la salud de las plantas.</p>\n    <p>¿Alguien más tiene consejos o experiencias para compartir sobre el cultivo de tomates?</p>', '2024-08-05 01:26:49', 36, 34567),
(140, '<p>EXCELENTE FORO</p><p><br></p>', '2024-08-05 21:12:54', 36, 373258736),
(148, '<h2>Nuevo álbum de \"The Lumineers\"</h2>\n    <h3>Reseña</h3>\n    <p>¡Gran recomendación! Escuché el álbum y me encantó cada pista. Es refrescante encontrar nueva música de calidad.</p>\n    <p>La mezcla de letras profundas y melodías pegajosas hace que sea un álbum digno de repetir.</p>\n    <p>¿Tienes más sugerencias de bandas similares que debería escuchar?</p>', '2024-08-07 15:04:19', 40, 22),
(152, '<p>Hola Marco,</p><p>La Unificación de Italia, también conocida como el Risorgimento, fue un proceso complejo que tuvo lugar entre 1815 y 1871. Entre los eventos clave, se encuentra la participación de figuras como Giuseppe Garibaldi, quien lideró la expedición de los Mil en 1860, y el Conde de Cavour, primer ministro del Reino de Cerdeña, que jugó un papel crucial en la diplomacia y las alianzas con Francia. La batalla de Solferino y la campaña de los Alpes también fueron momentos decisivos. El papel de Víctor Manuel II, el rey de Cerdeña, fue esencial para unificar las diferentes regiones bajo una sola bandera.</p>', '2024-08-08 02:09:23', 43, 11),
(153, '<p>Hola Karen,</p><p>La postura de Colombia es un reflejo de un movimiento global hacia la abolición de la pena de muerte. Históricamente, muchos países han utilizado la pena de muerte como un medio para mantener el orden y castigar los delitos graves. Sin embargo, en las últimas décadas, ha habido un cambio significativo en la percepción de este castigo, visto cada vez más como una violación de los derechos humanos fundamentales. Es alentador ver a países como Colombia liderando el camino hacia un mundo sin pena de muerte.</p>', '2024-08-08 02:13:48', 40, 1567894),
(154, '<p>Uno de los ejemplos más evidentes de la influencia romana es la arquitectura. Edificios emblemáticos como el Coliseo y el Panteón no solo son atractivos turísticos, sino que también han inspirado el diseño de edificios modernos. Además, muchas ciudades italianas, como Roma, Milán y Florencia, han sido construidas sobre las antiguas ciudades romanas, siguiendo en algunos casos los mismos trazados de calles y sistemas de alcantarillado. El uso de arcos, cúpulas y columnas en la arquitectura italiana moderna tiene sus raíces en las técnicas de construcción romanas.</p>', '2024-08-08 02:14:14', 43, 1567894),
(155, '<p><strong>La pena de muerte en Colombia</strong></p><p><br></p><p class=\"ql-align-justify\">Para conmemorar el&nbsp;<strong>Día Mundial contra la Pena de Muerte</strong>, celebrado desde el año 2003, quisimos recordar una de las últimas ocasiones en que esta medida se llevó a cabo de manera legal. El episodio en cuestión corresponde a la recreación del fusilamiento de los autores del atentado contra el Presidente de la República, Rafael Reyes, el 10 de febrero de 1906.</p><p class=\"ql-align-justify\">Este fragmento corresponde al último capítulo de la miniserie&nbsp;<em>Rafael Reyes, vencedor de imposibles</em>, emitida por los medios públicos en 1984. En ella se recrea con lujo de detalles ese escabroso momento de la historia colombiana en el que la pena capital se llevaba a cabo con pleno apego a la Constitución Política de 1886.</p><p class=\"ql-align-justify\">En su vida republicana la pena de muerte se aplicó para&nbsp;los&nbsp;delitos de&nbsp;rebelión,&nbsp;sedición,&nbsp;traición&nbsp;y&nbsp;conspiración. Famosas fueron las ejecuciones públicas de los conspiradores que atentaron contra la vida de Simón Bolívar o el fusilamiento de los prisioneros de la Batalla de Boyacá.</p><p class=\"ql-align-justify\">En esa primera&nbsp;mitad del siglo XIX se permitió la pena&nbsp;capital bajo la premisa de que el castigo ejemplar disminuía los&nbsp;índices de criminalidad que asolaban al país. En 1849 se abolió de manera parcial para estos delitos, pero se seguía aplicando para los delitos de parricidio, asesinato, envenenamiento, piratería y cuadrilla de malhechores. Se abolió totalmente en 1863 con la Constitución de los Estados&nbsp;Unidos de Colombia.</p><p class=\"ql-align-justify\">Sin embargo, se retomó con la nueva constitución política de 1886. Motivados por el aumento de la criminalidad durante el llamado Olimpo Radical, se buscaron penas más fuertes para garantizar el orden en la República. Pero, debajo de cuerda, era uno de los argumentos de aquellos opositores a las ideas liberales en el país, lo que se expresó en esta constitución de corte conservador y centralista, totalmente contraria a la Constitución de 1863.</p><p class=\"ql-align-justify\">El artículo 29 de la Carta Magna de 1886 prohibió llevar a cabo la pena capital para los delitos políticos. Se aplicaba para los delitos de “traición a la Patria en guerra extranjera, parricidio, asesinato, incendio, asalto en cuadrilla de malhechores, piratería, y ciertos delitos militares definidos por las leyes del ejército”. Pero a pesar de esa prohibición se usó en los periodos de guerra civil para castigar a los detractores&nbsp;liberales. Finalmente dejó de aplicarse por disposición del Acto Legislativo # 3 de 1910 de la Asamble Constituyente que ese año reformó la Constitución de Núñez y Caro.</p><p class=\"ql-align-justify\">El 7 de mayo de 1907 fue ejecutado en Quibdó el último hombre sentenciado a muerte en Colombia. Su nombre era Manuel Saturio Valencia.</p><p><br></p>', '2024-08-08 02:15:32', 40, 11),
(156, '<h2>Temas de conversación divertidos para hablar por teléfonooo</h2><p>A pesar de que muchos creen que comunicarse por llamadas puede ser algo tedioso y aburrido,&nbsp;<strong>existen muchos temas de conversación divertidos que se pueden utilizar para mantener una charla fluida</strong>.</p><p>Los temas para hablar por teléfono pueden ser muy variados. Además,&nbsp;<strong>las llamadas se mantienen como una de las formas de comunicación más efectivas</strong>, y cada tanto nos vemos en la necesidad de tener conversaciones por teléfono.</p><p><img src=\"https://crehana-blog.imgix.net/media/filer_public/fa/83/fa8399a0-021a-4df6-a6b7-4f9054bd2f89/temas-conversacion-divertidos-llamadas.jpg?auto=format&amp;q=50\" alt=\"Temas interesantes para hablar por teléfono\"></p>', '2024-08-08 02:19:22', 37, 22),
(157, '<h2>Temas para hablar por chat</h2><p>Los temas para hablar por chat, usualmente son un poco impersonales, la barrera que representa la pantalla del teléfono a veces impide que nos comuniquemos de manera cercana con las personas.</p><p>Si quieres saber&nbsp;<strong>cómo tener una buena conversación por WhatsApp</strong>, solo tienes que aprender a<strong>&nbsp;hacer buenas preguntas</strong>&nbsp;para que todo fluya de la mejor manera.</p><p>Con buenas preguntas no nos referimos a que le preguntes a la otra persona qué opina del clima, o cosas que pueden carecer un poco de sentido.&nbsp;Todo lo contrario, para tener temas interesantes para hablar, debes preguntar cosas que la otra persona esté interesada en responder y con las que se sienta a gusto.</p><p><img src=\"https://crehana-blog.imgix.net/media/filer_public/9d/ef/9defcc47-24d5-446f-99b3-bb223b12f3c9/temas-conversacion-divertidos-whatsapp.jpg?auto=format&amp;q=50\" alt=\"Temas de conversación para WhatsApp\"></p>', '2024-08-08 02:19:38', 37, 11),
(159, '<h2>¿Cómo tener temas de conversación?</h2><p>Tener conversaciones interesantes no siempre es posible,&nbsp;<strong>algunas veces nuestra mente se queda en blanco</strong>&nbsp;y simplemente no sabemos qué decir.</p><p>Saber cómo c<strong>omunicarse con los demás es una de las habilidades sociales más importantes</strong>&nbsp;que puede tener una persona, incluso hay quienes dicen que el inicio de la humanidad tuvo lugar cuando descubrimos el lenguaje.</p><p>Pero, ¿cómo saber si tienes temas de conversación divertidos?</p><p><br></p>', '2024-08-08 02:21:19', 35, 11),
(160, '<h3>7. ¿En qué continente está Ecuador?</h3><p>Ecuador es un país latinoamericano y, por tanto, se encuentra en América.</p><h3>8. ¿Dónde originaron los juegos olímpicos?</h3><p>Se originaron en Grecia. Se llaman así porque se celebraban en la ciudad de Olimpia.</p><h3>9. ¿Qué tipo de animal es la ballena?</h3><p>La ballena es un mamífero marino de hasta 30 metros de longitud.</p><h3>10. ¿De qué colores es la bandera de México?</h3><p>La bandera de México está compuesta por tres colores: verde, blanco y rojo.</p><h3>11. ¿Qué cantidad de huesos en el cuerpo humano?</h3><p>Un adulto tiene 206, mientras que los recién nacidos tienen alrededor de 300 huesos.</p>', '2024-08-08 02:23:58', 36, 11),
(164, '<p class=\"ql-align-justify\">Moodle ofrece&nbsp;<strong>5 tipos distintos de foros</strong>:</p><ul><li class=\"ql-align-justify\"><em>Cada persona plantea un tema</em>: cada alumno puede plantear un nuevo tema de debate y todos pueden responder.</li><li class=\"ql-align-justify\"><em>Debate sencillo</em>: intercambio de ideas sobre un solo tema, todo en una página.</li><li class=\"ql-align-justify\"><em>Foro estándar que aparece en formato blog</em>: Cualquiera puede iniciar un debate pero los temas se muestran en una página con enlaces \"<em>Discute este tema</em>\".</li><li class=\"ql-align-justify\"><em>Foro P y R (preguntas y respuestas)</em>: los estudiantes primero deben escribir su respuesta en el foro para poder ver las respuestas de los demás alumnos.</li><li class=\"ql-align-justify\"><em>Foro para uso general</em>: es un foro abierto donde cualquiera puede iniciar un debate, es el más utilizado para interactuar con los alumnos.</li></ul>', '2024-08-08 02:39:16', 43, 545554),
(167, '<p>El Fondo Europeo de Desarrollo Regional ​ es el principal instrumento financiero de la Política Regional y de Cohesión de la Comisión Europea destinado a contribuir a la corrección de los principales desequilibrios regionales dentro de la Unión.</p>', '2024-08-08 02:55:12', 43, 87654321),
(168, '<p><span style=\"color: rgb(32, 33, 34);\">El&nbsp;</span><strong style=\"color: rgb(32, 33, 34);\">idioma francés</strong><span style=\"color: rgb(32, 33, 34);\">&nbsp;es una lengua romance hablada en&nbsp;</span><a href=\"https://es.wikipedia.org/wiki/Francia\" target=\"_blank\" style=\"color: var(--color-progressive,#36c);\">Francia</a><span style=\"color: rgb(32, 33, 34);\">, origen de este idioma, así como en&nbsp;</span><a href=\"https://es.wikipedia.org/wiki/Canad%C3%A1\" target=\"_blank\" style=\"color: var(--color-progressive,#36c);\">Canadá</a><span style=\"color: rgb(32, 33, 34);\">,&nbsp;</span><a href=\"https://es.wikipedia.org/wiki/B%C3%A9lgica\" target=\"_blank\" style=\"color: var(--color-progressive,#36c);\">Bélgica</a><span style=\"color: rgb(32, 33, 34);\">,&nbsp;</span><a href=\"https://es.wikipedia.org/wiki/Luxemburgo\" target=\"_blank\" style=\"color: var(--color-progressive,#36c);\">Luxemburgo</a><span style=\"color: rgb(32, 33, 34);\">,&nbsp;</span><a href=\"https://es.wikipedia.org/wiki/Suiza\" target=\"_blank\" style=\"color: var(--color-progressive,#36c);\">Suiza</a><span style=\"color: rgb(32, 33, 34);\">&nbsp;y otros cincuenta y un países, la mayoría de los cuales formaban parte del antiguo&nbsp;</span><a href=\"https://es.wikipedia.org/wiki/Imperio_Colonial_Franc%C3%A9s\" target=\"_blank\" style=\"color: var(--color-progressive,#36c);\">Imperio colonial francés</a><span style=\"color: rgb(32, 33, 34);\">, especialmente en&nbsp;</span><a href=\"https://es.wikipedia.org/wiki/%C3%81frica\" target=\"_blank\" style=\"color: var(--color-progressive,#36c);\">África</a><span style=\"color: rgb(32, 33, 34);\">, así como la&nbsp;</span><a href=\"https://es.wikipedia.org/wiki/Rep%C3%BAblica_Democr%C3%A1tica_del_Congo\" target=\"_blank\" style=\"color: var(--color-progressive,#36c);\">República Democrática del Congo</a><span style=\"color: rgb(32, 33, 34);\">, antiguo&nbsp;</span><a href=\"https://es.wikipedia.org/wiki/Congo_Belga\" target=\"_blank\" style=\"color: var(--color-progressive,#36c);\">Congo Belga</a><span style=\"color: rgb(32, 33, 34);\">.</span></p>', '2024-08-08 02:57:51', 45, 87654321),
(169, '<p>La Academia Francesa (en francés: Académie française) es una institución encargada de regular y perfeccionar el idioma francés. Fue fundada en 1635 por el cardenal Richelieu durante el reinado de Luis XIII, lo que la hace una de las instituciones más antiguas de Francia. El fin fundamental con el que se creó fue el de normalizar la lengua francesa. Se compone de cuarenta miembros elegidos por sus iguales, llamados «los Inmortales». Es la primera de las cinco academias del Instituto de Francia.</p>', '2024-08-08 02:58:16', 45, 87654321),
(175, '<p>La tecnología digital ha revolucionado el aprendizaje en el aula, permitiendo a los estudiantes acceder a una vasta cantidad de información y recursos interactivos. Herramientas como las pizarras digitales, las aplicaciones educativas y los dispositivos móviles han facilitado un aprendizaje más dinámico y personalizado. Sin embargo, los docentes enfrentan desafíos significativos, como la falta de capacitación adecuada, la resistencia al cambio y las desigualdades en el acceso a la tecnología entre estudiantes.</p><p>JYGJYGGKJ</p>', '2024-08-08 17:50:03', 36, 45678328),
(176, '<p>En mi experiencia, las tecnologías digitales han potenciado la motivación y el compromiso de los estudiantes. Sin embargo, uno de los principales desafíos es la gestión del tiempo, ya que la preparación y adaptación de materiales digitales requieren más tiempo y esfuerzo. Además, es crucial encontrar un equilibrio para evitar que los estudiantes se distraigan con el uso indebido de dispositivos tecnológicos.</p><p><br></p>', '2024-08-08 17:54:58', 48, 545554);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_rol`
--

CREATE TABLE `tb_rol` (
  `id_rol` int(11) NOT NULL,
  `nom_rol` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_rol`
--

INSERT INTO `tb_rol` (`id_rol`, `nom_rol`) VALUES
(1, 'Estudiante'),
(2, 'Docente'),
(3, 'Admin'),
(4, 'SuperAdmin');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_tipo_doc`
--

CREATE TABLE `tb_tipo_doc` (
  `id_tipo` int(11) NOT NULL,
  `nom_tipo` varchar(50) NOT NULL,
  `estado` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_tipo_doc`
--

INSERT INTO `tb_tipo_doc` (`id_tipo`, `nom_tipo`, `estado`) VALUES
(1, 'Biografía', 1),
(2, 'Ensayo', 1),
(3, 'Novela', 1),
(4, 'Poesía', 1),
(5, 'Manual', 1),
(12, 'Poema', 1),
(13, 'Viaje', 0),
(14, 'Científico', 1),
(15, 'Monografías', 1),
(16, 'Revistas', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_tipo_foro`
--

CREATE TABLE `tb_tipo_foro` (
  `id_tp_foro` int(11) NOT NULL,
  `nom_tp_foro` varchar(50) NOT NULL,
  `estado` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_tipo_foro`
--

INSERT INTO `tb_tipo_foro` (`id_tp_foro`, `nom_tp_foro`, `estado`) VALUES
(1, 'Discusión', 1),
(2, 'Investigación', 1),
(3, 'Opinión', 1),
(4, 'Pregunta y respuestas', 1),
(5, 'General', 1),
(7, 'Plantear tema', 1),
(8, 'Biografía', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tb_usuarios`
--

CREATE TABLE `tb_usuarios` (
  `doc_usua` int(10) NOT NULL,
  `nom_usua` varchar(40) NOT NULL,
  `ape_usua` varchar(40) NOT NULL,
  `correo_usua` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `id_rol_fk` int(11) NOT NULL,
  `fecha_registro` datetime NOT NULL,
  `id_estado_fk` int(11) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tb_usuarios`
--

INSERT INTO `tb_usuarios` (`doc_usua`, `nom_usua`, `ape_usua`, `correo_usua`, `password`, `id_rol_fk`, `fecha_registro`, `id_estado_fk`) VALUES
(1, 'Admin', 'jose', 'adminjose@gmail.com', 'a01fe639a59421d411d910abfcb10b0928808f58b0579ab126ca8959f58705ac', 4, '2024-07-04 04:34:27', 1),
(7, 'Cristiano ronaldo', 'dos santos aveiro', 'cr7@gmail.com', 'e5a7479704eb41df9ff1dfa7c09d9d8f32ef583e7186ab3ec5f2887bbdbd2e53', 3, '2024-08-01 00:07:56', 1),
(9, 'Abel', 'Camargo', 'abelcO@gmail.com', '0e18d6d8185eb51d8c1c8cc6ed988afff0886dda40108f9b336c3f6564db32c0', 1, '2024-07-27 20:17:00', 1),
(10, 'James ESTUDIANTE', 'Rodriguez', 'hgod0244@gmail.com', '112', 1, '2024-07-03 19:25:06', 1),
(11, 'Abel Sneyder', 'Camargo Ortiz', 'abelcamar@gmail.com', 'a01fe639a59421d411d910abfcb10b0928808f58b0579ab126ca8959f58705ac', 1, '2024-07-02 23:27:19', 1),
(12, 'Abel Sneyder', 'Camargo Ortiz', 'abelcgo1@gmail.com', '09544803d5b4ed6076def6cb9f340f48a8fd3d73f4d3922885c4b751c5697083', 1, '2024-07-03 01:01:01', 1),
(22, 'María Magdalena', 'López', 'abescamargo@soy.sena.edu.co', 'a01fe639a59421d411d910abfcb10b0928808f58b0579ab126ca8959f58705ac', 3, '2024-07-03 20:37:17', 1),
(50, 'Humberto', 'Pena', 'humbertopena@gmail.com.co', '09544803d5b4ed6076def6cb9f340f48a8fd3d73f4d3922885c4b751c5697083', 1, '2024-07-27 22:05:37', 1),
(68, 'Carlos Humberto', 'Camargo', 'camcarl@gmail.com', '81a30d8d7d78edbb008d5160eb13813ae3412037fee60123efd1e5dce94b9c6c', 1, '2024-07-27 21:16:35', 1),
(88, 'Abelito', 'Camargo', 'grdgd4469444@gmail.com', '09544803d5b4ed6076def6cb9f340f48a8fd3d73f4d3922885c4b751c5697083', 2, '2024-07-21 00:16:09', 1),
(107, 'Leonel andres', 'Messi', 'leomessi@gmail.com', '608d54d703dc7164e1a13bfa075034bbe265e627d3be8c24051adf3f82597caf', 3, '2024-08-03 16:31:50', 1),
(109, 'Carlos', 'Calero', 'carlitos@gmail.com', '6060', 1, '2024-07-03 16:18:49', 0),
(444, 'Adelaida', 'Castro', 'adelita@gmail.com', 'e165ceaf269d82338e9cab6119e2c8e91a04fd2b2a6dde17483014db3a8ade93', 3, '2024-07-31 21:37:03', 1),
(1078, 'Leonel andres', 'Messi', 'leomessisi@gmail.com', 'd9f8e5f8a711b88e5cdb379bd0f58bfb1d3563c133f3eaf39f0cd00057807056', 3, '2024-08-03 16:34:11', 1),
(2121, 'carla', 'Chavez', 'bartolome@gmail.com', '652baa8aa4a23b28b609be7fe67135f1d3e92b5d4663ad125cad0a7613bd212f', 2, '2024-07-21 18:42:52', 0),
(5478, 'JuanUHF', 'Pérez', 'juanpery5rez@ejemplo.com', '09544803d5b4ed6076def6cb9f340f48a8fd3d73f4d3922885c4b751c5697083', 2, '2024-07-20 22:34:52', 1),
(6666, 'tania', 'Chavez', 'bartolhtome@gmail.com', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 2, '2024-07-21 22:27:27', 1),
(9999, 'sofia', 'Chavez', 'barto456htome@gmail.com', '173af653133d964edfc16cafe0aba33c8f500a07f3ba3f81943916910c257705', 2, '2024-07-21 22:40:22', 1),
(13814, 'Humberto ', 'Peña', 'humberto@gmail.com', 'fae7755a0bda8d21e27f16e47db760b92f0110f906f563a938ecd84ee5ac6178', 2, '2024-07-22 14:59:11', 1),
(18975, 'Christopherr', 'Alexey', 'cristo453@gmail.com', '09544803d5b4ed6076def6cb9f340f48a8fd3d73f4d3922885c4b751c5697083', 1, '2024-07-25 16:47:06', 1),
(34567, 'Carla', 'Pinzón', 'carlita@gmail.com', '1515ecead557e887371835c14f8edc3f422f0d16859b802d37b2c8b2f0b877c6', 2, '2024-08-05 01:26:28', 1),
(35569, 'grtdrhjrer', 'grdgdr', 'grdgd@gmail.com', '09544803d5b4ed6076def6cb9f340f48a8fd3d73f4d3922885c4b751c5697083', 2, '2024-07-18 22:27:05', 0),
(55656, 'Abel', 'Camargo', 'abelcamargo1@gmail.com', '09544803d5b4ed6076def6cb9f340f48a8fd3d73f4d3922885c4b751c5697083', 1, '2024-07-26 22:32:43', 1),
(99996, 'ivan', 'Chavez', 'barto456htome@gmail.com', '4a36ab0de4371484da6442bcea330efd8ea68e55859a603648f157374df4bbe2', 2, '2024-07-21 23:23:23', 1),
(234237, 'tetyttitasy', 'trrtrtrtYu', 'ggr@gmail.com', '34Agrdgrdg', 2, '2024-07-18 21:36:27', 1),
(345345, 'Juan Carlos', 'Pérez Gómez', 'juan.perez@ejemplo.com', '123', 2, '2024-07-18 00:03:37', 1),
(545554, 'Juan', 'Ortiz', 'juanperez@ejemplo.com', 'a01fe639a59421d411d910abfcb10b0928808f58b0579ab126ca8959f58705ac', 2, '2024-07-17 23:43:58', 1),
(546456, 'Abel', 'cañon', 'abelcamafesfrgo1@gmail.com', '09544803d5b4ed6076def6cb9f340f48a8fd3d73f4d3922885c4b751c5697083', 2, '2024-08-05 01:14:14', 1),
(999967, 'eduardo', 'Chavez', 'barto456htome@gmail.com', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 2, '2024-07-21 23:34:09', 0),
(1096066, 'Carlos', 'García', 'carlos.garcia@ejemplo.com', 'contrasena3', 2, '2024-07-03 20:37:17', 1),
(1098803, 'Abelito', 'Orduz', 'abelpicon1@gmail.com', '652baa8aa4a23b28b609be7fe67135f1d3e92b5d4663ad125cad0a7613bd212f', 1, '2024-07-25 15:39:26', 1),
(1567894, 'Karen', 'Florez', 'enzy35@gmail.com', 'a01fe639a59421d411d910abfcb10b0928808f58b0579ab126ca8959f58705ac', 1, '2024-08-07 14:07:11', 1),
(3234569, 'James PROFE', 'Chavez', 'barto45tr6htome@gmail.com', '09544803d5b4ed6076def6cb9f340f48a8fd3d73f4d3922885c4b751c5697083', 2, '2024-07-21 23:55:04', 1),
(5458901, 'Carlos Rios', 'López Torresst', 'carloslopez@example.com', '09544803d5b4ed6076def6cb9f340f48a8fd3d73f4d3922885c4b751c5697083', 2, '2024-07-18 00:07:15', 1),
(9999678, 'carlos', 'Chavez', 'barto456htome@gmail.com', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3', 2, '2024-07-21 23:34:47', 1),
(10969999, 'Abel', 'Camargo', 'abelcama5345345go1@gmail.com', 'd94b3c7f8cca6111f89cb13978c72647677ce7939224708eeda2be189a3858f7', 1, '2024-07-25 16:37:44', 1),
(10988345, 'Abelito', 'Chía', 'abelpict4ete4on1@gmail.com', '8f8d65533e8d8fac7c6d5731b0b0a265dfc12f0946031d90ce99f3ac06a23aa3', 1, '2024-07-25 15:47:27', 1),
(34567890, 'José Luis', 'Martínez Díaz', 'jose.martinez@example.com', '789', 2, '2024-07-18 00:04:25', 1),
(45678328, 'Enzy', 'Zulay', 'jean@gmail.com', 'a01fe639a59421d411d910abfcb10b0928808f58b0579ab126ca8959f58705ac', 1, '2024-08-08 17:47:09', 1),
(45678901, 'Ana Patricia', 'Gómez Ramírez', 'ana.gomez@example.com', '321', 2, '2024-07-18 00:06:41', 1),
(60340319, 'Luz', 'Ortiz', 'luzortiz09@gmail.com', 'dbdbb30b566f0ac10e0c8a9767fb81cebf64d9954a12a5e06708cdb90433a7a5', 1, '2024-07-25 16:05:01', 1),
(60606045, 'Abel', 'Castaño', 'castano@gmail.com', '09544803d5b4ed6076def6cb9f340f48a8fd3d73f4d3922885c4b751c5697083', 2, '2024-08-04 23:14:01', 1),
(67895432, 'Eduardo', 'Torres', 'trtrtrt@faculty.university.edu', 'a01fe639a59421d411d910abfcb10b0928808f58b0579ab126ca8959f58705ac', 2, '2024-08-08 17:59:12', 1),
(69696969, 'Abel', 'Camargo', 'abelcamar6767go1@gmail.com', '09544803d5b4ed6076def6cb9f340f48a8fd3d73f4d3922885c4b751c5697083', 3, '2024-08-04 19:53:54', 1),
(87654321, 'Ana Camila', 'Torres', 'ana.torres@faculty.university.edu', 'a01fe639a59421d411d910abfcb10b0928808f58b0579ab126ca8959f58705ac', 1, '2024-08-08 02:54:09', 1),
(109880392, 'Erika', 'Sanchez', 'erika@gmail.com', 'c37ee1d7892f5c20b2f9d6e106f52ec030a1cbd33bd9d98e34b86dee0deb72e6', 2, '2024-07-27 20:28:51', 1),
(233456789, 'María Fernanda', 'Rodríguez López', 'maria.rodriguez@example.com', '4565', 2, '2024-07-18 00:04:05', 1),
(373258736, 'Jhon', 'Becerra', 'Jhonbecerra342@gmail.com', '49dccb130a4df92211d28d94c6f53d76095f862dfac4eb7866f8c599ef715f7a', 3, '2024-08-05 21:09:00', 1),
(1096063324, 'Abel', 'Camargo', 'abelcama345345345go1@gmail.com', '4ecd3e277b371a3d6fee2ec8db57b17b92bae490e376f68b2e133210666a5ad2', 1, '2024-07-25 16:21:54', 1),
(1096063951, 'Abel', 'Camargo', 'jchernandez1047@misena.edu.co', 'a01fe639a59421d411d910abfcb10b0928808f58b0579ab126ca8959f58705ac', 2, '2024-08-01 21:36:37', 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `tb_asignaturas`
--
ALTER TABLE `tb_asignaturas`
  ADD PRIMARY KEY (`id_asig`);

--
-- Indices de la tabla `tb_documento`
--
ALTER TABLE `tb_documento`
  ADD PRIMARY KEY (`id_doc`),
  ADD KEY `id_asig_fk` (`id_asig_fk`),
  ADD KEY `id_idioma_fk` (`id_idioma_fk`),
  ADD KEY `id_tipo_fk` (`id_tipo_fk`),
  ADD KEY `fk_tb_documento_doc_usua_fk` (`doc_docente_fk`);

--
-- Indices de la tabla `tb_estado_usua`
--
ALTER TABLE `tb_estado_usua`
  ADD PRIMARY KEY (`id_estado`);

--
-- Indices de la tabla `tb_foro`
--
ALTER TABLE `tb_foro`
  ADD PRIMARY KEY (`id_foro`),
  ADD KEY `id_asig_fk` (`id_asig_fk`),
  ADD KEY `doc_usua_fk` (`doc_usua_fk`),
  ADD KEY `id_idioma_fk` (`id_idioma_fk`),
  ADD KEY `id_tpfr_fk` (`id_tpfr_fk`);

--
-- Indices de la tabla `tb_idiomas`
--
ALTER TABLE `tb_idiomas`
  ADD PRIMARY KEY (`id_idioma`);

--
-- Indices de la tabla `tb_respuesta_foro`
--
ALTER TABLE `tb_respuesta_foro`
  ADD PRIMARY KEY (`id_respu`),
  ADD KEY `id_foro_fk` (`id_foro_fk`),
  ADD KEY `doc_usua_fk` (`doc_usua_fk`);

--
-- Indices de la tabla `tb_rol`
--
ALTER TABLE `tb_rol`
  ADD PRIMARY KEY (`id_rol`);

--
-- Indices de la tabla `tb_tipo_doc`
--
ALTER TABLE `tb_tipo_doc`
  ADD PRIMARY KEY (`id_tipo`);

--
-- Indices de la tabla `tb_tipo_foro`
--
ALTER TABLE `tb_tipo_foro`
  ADD PRIMARY KEY (`id_tp_foro`);

--
-- Indices de la tabla `tb_usuarios`
--
ALTER TABLE `tb_usuarios`
  ADD PRIMARY KEY (`doc_usua`),
  ADD KEY `id_rol_fk` (`id_rol_fk`),
  ADD KEY `fk_estado_usuario` (`id_estado_fk`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `tb_asignaturas`
--
ALTER TABLE `tb_asignaturas`
  MODIFY `id_asig` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT de la tabla `tb_documento`
--
ALTER TABLE `tb_documento`
  MODIFY `id_doc` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=104;

--
-- AUTO_INCREMENT de la tabla `tb_foro`
--
ALTER TABLE `tb_foro`
  MODIFY `id_foro` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=49;

--
-- AUTO_INCREMENT de la tabla `tb_idiomas`
--
ALTER TABLE `tb_idiomas`
  MODIFY `id_idioma` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT de la tabla `tb_respuesta_foro`
--
ALTER TABLE `tb_respuesta_foro`
  MODIFY `id_respu` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=177;

--
-- AUTO_INCREMENT de la tabla `tb_rol`
--
ALTER TABLE `tb_rol`
  MODIFY `id_rol` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `tb_tipo_doc`
--
ALTER TABLE `tb_tipo_doc`
  MODIFY `id_tipo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT de la tabla `tb_tipo_foro`
--
ALTER TABLE `tb_tipo_foro`
  MODIFY `id_tp_foro` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `tb_usuarios`
--
ALTER TABLE `tb_usuarios`
  MODIFY `doc_usua` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1567894577;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `tb_documento`
--
ALTER TABLE `tb_documento`
  ADD CONSTRAINT `fk_tb_documento_doc_usua_fk` FOREIGN KEY (`doc_docente_fk`) REFERENCES `tb_usuarios` (`doc_usua`),
  ADD CONSTRAINT `tb_documento_ibfk_2` FOREIGN KEY (`id_asig_fk`) REFERENCES `tb_asignaturas` (`id_asig`),
  ADD CONSTRAINT `tb_documento_ibfk_3` FOREIGN KEY (`id_idioma_fk`) REFERENCES `tb_idiomas` (`id_idioma`),
  ADD CONSTRAINT `tb_documento_ibfk_4` FOREIGN KEY (`id_tipo_fk`) REFERENCES `tb_tipo_doc` (`id_tipo`);

--
-- Filtros para la tabla `tb_foro`
--
ALTER TABLE `tb_foro`
  ADD CONSTRAINT `tb_foro_ibfk_1` FOREIGN KEY (`id_asig_fk`) REFERENCES `tb_asignaturas` (`id_asig`),
  ADD CONSTRAINT `tb_foro_ibfk_2` FOREIGN KEY (`doc_usua_fk`) REFERENCES `tb_usuarios` (`doc_usua`),
  ADD CONSTRAINT `tb_foro_ibfk_3` FOREIGN KEY (`id_idioma_fk`) REFERENCES `tb_idiomas` (`id_idioma`),
  ADD CONSTRAINT `tb_foro_ibfk_4` FOREIGN KEY (`id_tpfr_fk`) REFERENCES `tb_tipo_foro` (`id_tp_foro`);

--
-- Filtros para la tabla `tb_respuesta_foro`
--
ALTER TABLE `tb_respuesta_foro`
  ADD CONSTRAINT `tb_respuesta_foro_ibfk_1` FOREIGN KEY (`id_foro_fk`) REFERENCES `tb_foro` (`id_foro`),
  ADD CONSTRAINT `tb_respuesta_foro_ibfk_2` FOREIGN KEY (`doc_usua_fk`) REFERENCES `tb_usuarios` (`doc_usua`);

--
-- Filtros para la tabla `tb_usuarios`
--
ALTER TABLE `tb_usuarios`
  ADD CONSTRAINT `fk_estado_usuario` FOREIGN KEY (`id_estado_fk`) REFERENCES `tb_estado_usua` (`id_estado`),
  ADD CONSTRAINT `tb_usuarios_ibfk_1` FOREIGN KEY (`id_rol_fk`) REFERENCES `tb_rol` (`id_rol`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
