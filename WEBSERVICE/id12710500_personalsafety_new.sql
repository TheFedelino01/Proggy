-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Mag 09, 2020 alle 20:00
-- Versione del server: 10.1.38-MariaDB
-- Versione PHP: 7.3.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `id12710500_personalsafety_new`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `admin`
--

CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `username` varchar(16) COLLATE utf8_unicode_ci NOT NULL,
  `password` char(32) COLLATE utf8_unicode_ci NOT NULL,
  `idEnte` int(11) DEFAULT NULL,
  `nome` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `cognome` varchar(32) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dump dei dati per la tabella `admin`
--

INSERT INTO `admin` (`id`, `username`, `password`, `idEnte`, `nome`, `cognome`) VALUES
(1, 'admin', '5f4dcc3b5aa765d61d8327deb882cf99', 1, 'Giacomo', 'Orsenigo');

-- --------------------------------------------------------

--
-- Struttura della tabella `ap`
--

CREATE TABLE `ap` (
  `id` int(11) NOT NULL,
  `mac` varchar(18) COLLATE utf8_unicode_ci NOT NULL,
  `ssid` varchar(16) COLLATE utf8_unicode_ci NOT NULL,
  `latitudine` varchar(16) COLLATE utf8_unicode_ci NOT NULL,
  `longitudine` varchar(16) COLLATE utf8_unicode_ci NOT NULL,
  `idEnte` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `cartellaclinica`
--

CREATE TABLE `cartellaclinica` (
  `id` int(11) NOT NULL,
  `allergie` text COLLATE utf8_unicode_ci,
  `fermaci` text COLLATE utf8_unicode_ci,
  `gruppoSanguigno` varchar(4) COLLATE utf8_unicode_ci DEFAULT NULL,
  `note` text COLLATE utf8_unicode_ci,
  `idCliente` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dump dei dati per la tabella `cartellaclinica`
--

INSERT INTO `cartellaclinica` (`id`, `allergie`, `fermaci`, `gruppoSanguigno`, `note`, `idCliente`) VALUES
(1, 'ambrosia', 'farmaco importante', NULL, 'nessuna', 18),
(2, 'ambrosia', 'tachipirina', NULL, 'nessuna', 18);

-- --------------------------------------------------------

--
-- Struttura della tabella `cliente`
--

CREATE TABLE `cliente` (
  `id` int(11) NOT NULL,
  `cf` char(16) COLLATE utf8_unicode_ci NOT NULL,
  `foto` varchar(40) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'default.png',
  `nome` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `cognome` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `dataNascita` date NOT NULL,
  `sesso` enum('M','F') COLLATE utf8_unicode_ci NOT NULL,
  `username` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `password` char(32) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dump dei dati per la tabella `cliente`
--

INSERT INTO `cliente` (`id`, `cf`, `foto`, `nome`, `cognome`, `dataNascita`, `sesso`, `username`, `password`) VALUES
(1, 'DACONTROLLARE', 'default.png', 'Ugo', 'Proserpio', '1980-02-15', 'M', 'test', '5f4dcc3b5aa765d61d8327deb882cf99'),
(18, 'NONDICO', 'foto Saccani Federico.jpg', 'Federico', 'Saccani', '2001-11-12', 'M', 'federico', '5f4dcc3b5aa765d61d8327deb882cf99'),
(24, 'NONDICO2', 'Koala by TheFedelino01.png', 'Luca', 'Minecraft', '2001-04-28', '', 'minekraft', '5f4dcc3b5aa765d61d8327deb882cf99'),
(25, 'v Data di Nascit', '19inchs.jpg', 'Data di Nascita  08/04/2020 Sess', 'Data di Nascita  08/04/2020 Sess', '2020-04-08', '', 'Data di Nascita  08/04/2020 Sess', '11b37b126f80faae2de7161e4e53b8e5');

-- --------------------------------------------------------

--
-- Struttura della tabella `collisione`
--

CREATE TABLE `collisione` (
  `id` int(11) NOT NULL,
  `idScheda` int(11) NOT NULL,
  `idEstraneo` int(11) NOT NULL,
  `dataOra` datetime NOT NULL,
  `latitudine` varchar(16) COLLATE utf8_unicode_ci NOT NULL,
  `longitudine` varchar(16) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dump dei dati per la tabella `collisione`
--

INSERT INTO `collisione` (`id`, `idScheda`, `idEstraneo`, `dataOra`, `latitudine`, `longitudine`) VALUES
(1, 1, 2, '2002-05-30 09:00:00', '12.56', '12.56'),
(2, 1, 2, '2002-05-30 09:00:00', '12.56', '12.56'),
(3, 1, 2, '2002-05-30 09:00:00', '12.56', '12.56');

-- --------------------------------------------------------

--
-- Struttura della tabella `ente`
--

CREATE TABLE `ente` (
  `cod` int(11) NOT NULL,
  `nome` varchar(32) COLLATE utf8_unicode_ci NOT NULL,
  `img` varchar(40) COLLATE utf8_unicode_ci NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dump dei dati per la tabella `ente`
--

INSERT INTO `ente` (`cod`, `nome`, `img`) VALUES
(1, 'Esselunga Paina', 'esselungaPaina.png'),
(2, 'U2 Carugo', 'u2Carugo.png');

-- --------------------------------------------------------

--
-- Struttura della tabella `log`
--

CREATE TABLE `log` (
  `id` int(11) NOT NULL,
  `dataOra` datetime NOT NULL,
  `ip` varchar(16) COLLATE utf8_unicode_ci NOT NULL,
  `idAdmin` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Struttura della tabella `posizione`
--

CREATE TABLE `posizione` (
  `id` int(11) NOT NULL,
  `latitudine` varchar(16) COLLATE utf8_unicode_ci NOT NULL,
  `longitudine` varchar(16) COLLATE utf8_unicode_ci NOT NULL,
  `dataOra` datetime NOT NULL,
  `idScheda` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dump dei dati per la tabella `posizione`
--

INSERT INTO `posizione` (`id`, `latitudine`, `longitudine`, `dataOra`, `idScheda`) VALUES
(1, '45.687345', '9.179013', '2020-04-19 00:03:00', 1),
(2, '45.687977', '9.179388', '2020-04-19 00:04:00', 1),
(3, '45.688206', '9.178835', '2020-04-19 00:20:00', 1),
(4, '45.687132', '9.178024', '2020-05-05 00:00:00', 2),
(5, '45.686900', '9.179100', '2020-05-06 05:00:00', 2);

-- --------------------------------------------------------

--
-- Struttura della tabella `scheda`
--

CREATE TABLE `scheda` (
  `cod` int(11) NOT NULL,
  `idEnte` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dump dei dati per la tabella `scheda`
--

INSERT INTO `scheda` (`cod`, `idEnte`) VALUES
(1, 1),
(2, 1),
(8, 1),
(3, 2),
(4, 2),
(5, 2),
(6, 2),
(7, 2),
(9, 2),
(10, 2),
(11, 2),
(12, 2),
(13, 2);

-- --------------------------------------------------------

--
-- Struttura della tabella `utilizza`
--

CREATE TABLE `utilizza` (
  `idScheda` int(11) NOT NULL,
  `idCliente` int(11) NOT NULL,
  `dataOraInizio` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `dataOraFine` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dump dei dati per la tabella `utilizza`
--

INSERT INTO `utilizza` (`idScheda`, `idCliente`, `dataOraInizio`, `dataOraFine`) VALUES
(1, 1, '2020-05-09 13:00:00', '2020-05-09 13:05:00'),
(1, 18, '2020-05-19 12:00:00', NULL);

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD KEY `Amministra` (`idEnte`);

--
-- Indici per le tabelle `ap`
--
ALTER TABLE `ap`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `mac` (`mac`),
  ADD KEY `collocato` (`idEnte`);

--
-- Indici per le tabelle `cartellaclinica`
--
ALTER TABLE `cartellaclinica`
  ADD PRIMARY KEY (`id`),
  ADD KEY `caratterizza` (`idCliente`);

--
-- Indici per le tabelle `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `user` (`username`),
  ADD UNIQUE KEY `cf` (`cf`);

--
-- Indici per le tabelle `collisione`
--
ALTER TABLE `collisione`
  ADD PRIMARY KEY (`id`),
  ADD KEY `Appartiene` (`idScheda`),
  ADD KEY `Collide` (`idEstraneo`);

--
-- Indici per le tabelle `ente`
--
ALTER TABLE `ente`
  ADD PRIMARY KEY (`cod`);

--
-- Indici per le tabelle `log`
--
ALTER TABLE `log`
  ADD PRIMARY KEY (`id`),
  ADD KEY `esegue` (`idAdmin`);

--
-- Indici per le tabelle `posizione`
--
ALTER TABLE `posizione`
  ADD PRIMARY KEY (`id`),
  ADD KEY `Registra` (`idScheda`);

--
-- Indici per le tabelle `scheda`
--
ALTER TABLE `scheda`
  ADD PRIMARY KEY (`cod`),
  ADD KEY `Possiede` (`idEnte`);

--
-- Indici per le tabelle `utilizza`
--
ALTER TABLE `utilizza`
  ADD PRIMARY KEY (`idScheda`,`idCliente`,`dataOraInizio`),
  ADD KEY `utilizza` (`idCliente`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT per la tabella `ap`
--
ALTER TABLE `ap`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `cliente`
--
ALTER TABLE `cliente`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT per la tabella `collisione`
--
ALTER TABLE `collisione`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT per la tabella `ente`
--
ALTER TABLE `ente`
  MODIFY `cod` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT per la tabella `log`
--
ALTER TABLE `log`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `posizione`
--
ALTER TABLE `posizione`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT per la tabella `scheda`
--
ALTER TABLE `scheda`
  MODIFY `cod` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `admin`
--
ALTER TABLE `admin`
  ADD CONSTRAINT `Amministra` FOREIGN KEY (`idEnte`) REFERENCES `ente` (`cod`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Limiti per la tabella `ap`
--
ALTER TABLE `ap`
  ADD CONSTRAINT `collocato` FOREIGN KEY (`idEnte`) REFERENCES `ente` (`cod`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `cartellaclinica`
--
ALTER TABLE `cartellaclinica`
  ADD CONSTRAINT `caratterizza` FOREIGN KEY (`idCliente`) REFERENCES `cliente` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `collisione`
--
ALTER TABLE `collisione`
  ADD CONSTRAINT `Appartiene` FOREIGN KEY (`idScheda`) REFERENCES `scheda` (`cod`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `Collide` FOREIGN KEY (`idEstraneo`) REFERENCES `scheda` (`cod`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Limiti per la tabella `log`
--
ALTER TABLE `log`
  ADD CONSTRAINT `esegue` FOREIGN KEY (`idAdmin`) REFERENCES `admin` (`id`) ON UPDATE CASCADE;

--
-- Limiti per la tabella `posizione`
--
ALTER TABLE `posizione`
  ADD CONSTRAINT `Registra` FOREIGN KEY (`idScheda`) REFERENCES `scheda` (`cod`) ON UPDATE CASCADE;

--
-- Limiti per la tabella `scheda`
--
ALTER TABLE `scheda`
  ADD CONSTRAINT `Possiede` FOREIGN KEY (`idEnte`) REFERENCES `ente` (`cod`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `utilizza`
--
ALTER TABLE `utilizza`
  ADD CONSTRAINT `utilizza` FOREIGN KEY (`idCliente`) REFERENCES `cliente` (`id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `utilizzata` FOREIGN KEY (`idScheda`) REFERENCES `scheda` (`cod`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
