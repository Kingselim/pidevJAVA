-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : mar. 30 avr. 2024 à 15:59
-- Version du serveur : 10.4.27-MariaDB
-- Version de PHP : 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `givest`
--

-- --------------------------------------------------------

--
-- Structure de la table `account`
--

CREATE TABLE `account` (
  `id` int(11) NOT NULL,
  `id_association_id` int(11) NOT NULL,
  `solde` double NOT NULL,
  `type_account` varchar(255) DEFAULT NULL,
  `date_creation` varchar(255) NOT NULL,
  `state` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `account`
--

INSERT INTO `account` (`id`, `id_association_id`, `solde`, `type_account`, `date_creation`, `state`) VALUES
(1, 1, 45000, 'collecte', '2003-12-02', 1);

-- --------------------------------------------------------

--
-- Structure de la table `action`
--

CREATE TABLE `action` (
  `id` int(11) NOT NULL,
  `name_action` varchar(255) NOT NULL,
  `description_action` varchar(500) NOT NULL,
  `date_action` date NOT NULL,
  `location` varchar(255) NOT NULL,
  `id_association` int(11) DEFAULT NULL,
  `image` varchar(255) NOT NULL,
  `updated_at` int(11) DEFAULT NULL,
  `organized_for` varchar(255) DEFAULT NULL,
  `target_amount` bigint(20) NOT NULL,
  `category` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `action`
--

INSERT INTO `action` (`id`, `name_action`, `description_action`, `date_action`, `location`, `id_association`, `image`, `updated_at`, `organized_for`, `target_amount`, `category`) VALUES
(1, 'hamachient', 'njlkbn piumnjzifzdfdkhljdf', '2024-03-08', 'hammamet', 1, 'GOPR0785.JPG', NULL, 'chient', 0, NULL),
(2, 'sauvetage', 'kjnufhrepuifnhuefuhz enf', '2024-03-15', 'tunis', 3, 'IMG_2251.JPG', NULL, 'lesgenspauvre', 0, NULL),
(3, 'actionnnn1', 'zegfzgzefzgzegzegezg', '2024-03-07', 'zegzegezg', 2, 'IMG_2276.JPG', NULL, 'zegezgezg', 1000, NULL),
(4, 'teeeeeeet', 'zefgzefgzefezgfzefzefezf', '2024-03-02', 'zefzefzef', 7, 'IMG_2285.JPG', NULL, 'zefzefzefzefzef', 10000, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `association`
--

CREATE TABLE `association` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `state` varchar(255) NOT NULL,
  `description` varchar(1000) NOT NULL,
  `id_user` int(11) DEFAULT NULL,
  `statut` tinyint(1) NOT NULL,
  `facebook_adresse` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `association`
--

INSERT INTO `association` (`id`, `name`, `phone`, `email`, `city`, `state`, `description`, `id_user`, `statut`, `facebook_adresse`) VALUES
(1, 'chienchat', '50111079', 'chienchat@gmail.com', 'hammamet', '1', 'des produits pour les chiens et les chats', NULL, 1, NULL),
(2, 'chientchient', '50111079', 'chient@gmail.com', 'tunis', 'active', 'chientchieintklynbjgvdcgvfhbnjkljhdgfghjkjhgfdgvhjk', 1, 0, 'chientfb'),
(3, 'chameauccccc', '50111079', 'chient@gmail.com', 'tunis', 'active', ',lkmjhfgyutbyutyutzcufgyznegfigcefoihiuhfcearf', 3, 1, 'fjejnfiuaerbfjne'),
(5, 'cheveauc', '50111079', 'gaaloulselim@esprit.tn', 'nabeul', 'active', 'iuotrzutiouriouoiuriotueiruiueoirpoier', 3, 0, 'ehreiotuioeurtoherhheiht'),
(6, 'cheveauc', '50111079', 'chient@gmail.com', 'tunis', 'active', 'ljhgjghjfyfififffhggfhghjgjhgkghjgjhghghjgjh', 3, 0, 'jhgjhghjghjgjhghjgjhg'),
(7, 'cheveaucsdfs', '50111079', 'chient@gmail.com', 'tunis', 'active', 'ljhgjghjfyfififffhggfhghjgjhgkghjgjhghghjgjh', 3, 0, 'jhgjhghjghjgjhghjgjhg');

-- --------------------------------------------------------

--
-- Structure de la table `demande`
--

CREATE TABLE `demande` (
  `id` int(11) NOT NULL,
  `id_user_id` int(11) NOT NULL,
  `id_pret_id` int(11) NOT NULL,
  `state` varchar(255) NOT NULL,
  `type_pret` varchar(255) NOT NULL,
  `motif_pret` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `demande`
--

INSERT INTO `demande` (`id`, `id_user_id`, `id_pret_id`, `state`, `type_pret`, `motif_pret`) VALUES
(1, 3, 1, '1', 'fourbe', 'voiture'),
(2, 3, 1, 'active', 'besoin', 'moto'),
(3, 1, 1, 'active2', 'faza', 'bateau'),
(4, 1, 1, 'active2', 'faza', 'bateau');

-- --------------------------------------------------------

--
-- Structure de la table `demande_sponsoring`
--

CREATE TABLE `demande_sponsoring` (
  `id` int(11) NOT NULL,
  `idsponsoring_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `datedebut` date NOT NULL,
  `datefin` date NOT NULL,
  `budget` double NOT NULL,
  `nom_association` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `demande_sponsoring`
--

INSERT INTO `demande_sponsoring` (`id`, `idsponsoring_id`, `user_id`, `datedebut`, `datefin`, `budget`, `nom_association`) VALUES
(1, 1, 3, '2019-01-01', '2019-06-01', 7000, 'toyota'),
(2, 1, 3, '2019-01-01', '2019-07-01', 11111, 'toyota'),
(3, 1, 3, '2019-01-01', '2019-10-01', 825415, 'ford'),
(4, 2, 3, '2019-01-01', '2019-01-01', 758, 'slouma');

-- --------------------------------------------------------

--
-- Structure de la table `doctrine_migration_versions`
--

CREATE TABLE `doctrine_migration_versions` (
  `version` varchar(191) NOT NULL,
  `executed_at` datetime DEFAULT NULL,
  `execution_time` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `doctrine_migration_versions`
--

INSERT INTO `doctrine_migration_versions` (`version`, `executed_at`, `execution_time`) VALUES
('DoctrineMigrations\\Version20240306160005', '2024-03-06 17:00:08', 1212),
('DoctrineMigrations\\Version20240307110952', '2024-03-07 12:10:00', 488),
('DoctrineMigrations\\Version20240307182847', '2024-03-07 19:28:53', 374),
('DoctrineMigrations\\Version20240423182851', '2024-04-27 12:11:21', 166);

-- --------------------------------------------------------

--
-- Structure de la table `messenger_messages`
--

CREATE TABLE `messenger_messages` (
  `id` bigint(20) NOT NULL,
  `body` longtext NOT NULL,
  `headers` longtext NOT NULL,
  `queue_name` varchar(190) NOT NULL,
  `created_at` datetime NOT NULL,
  `available_at` datetime NOT NULL,
  `delivered_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `participant`
--

CREATE TABLE `participant` (
  `id` int(11) NOT NULL,
  `idseminar_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `function` varchar(255) NOT NULL,
  `phone` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `participant`
--

INSERT INTO `participant` (`id`, `idseminar_id`, `name`, `last_name`, `function`, `phone`) VALUES
(3, 1, 'slouma', 'gg', 'Etudiant', 78787878),
(4, 1, 'slouma', 'gg', 'Etudiant', 78787878),
(6, 1, 'sloumaaaaa3', 'jbgjhbjvhbjh', 'Etudiant', 50111079),
(7, 1, 'hgbkgfbk', 'mhnbuytfvgn', 'Etudiant', 50111079),
(8, 1, 'jkbhvgc', 'lkjgfgv', 'Etudiant', 20111485),
(9, 1, 'slouma', 'knhjg', 'Etudiant', 50111079);

-- --------------------------------------------------------

--
-- Structure de la table `pret`
--

CREATE TABLE `pret` (
  `id` int(11) NOT NULL,
  `montant_pret` double NOT NULL,
  `taux_interet` double NOT NULL,
  `duree` varchar(255) NOT NULL,
  `nom_association` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `pret`
--

INSERT INTO `pret` (`id`, `montant_pret`, `taux_interet`, `duree`, `nom_association`) VALUES
(1, 555, 3, '12-06-03', 'nissan'),
(2, 777, 10, '12-06-03', 'chiechat');

-- --------------------------------------------------------

--
-- Structure de la table `seminar`
--

CREATE TABLE `seminar` (
  `id` int(11) NOT NULL,
  `iduserasso_id` int(11) NOT NULL,
  `date_seminar` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `nomassociation` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `seminar`
--

INSERT INTO `seminar` (`id`, `iduserasso_id`, `date_seminar`, `description`, `nomassociation`) VALUES
(1, 3, '2003-10-03', 'utytyuytyuttyuhhhhh', 'chientchat'),
(3, 3, '2003-10-03', 'hhhhhhhhhhhhhhhhhhhhh', 'vache');

-- --------------------------------------------------------

--
-- Structure de la table `souvenir`
--

CREATE TABLE `souvenir` (
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `date` varchar(255) DEFAULT NULL,
  `idsouvenir` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `souvenir`
--

INSERT INTO `souvenir` (`email`, `password`, `date`, `idsouvenir`) VALUES
('yo@gmail.com', 'ooooo', '2024-04-30', 2),
('waaaaa@gmail.com', 'waaa', '2024-04-30', 3),
('hatem@gmail.com', 'hatouma', '2024-04-30', 6);

-- --------------------------------------------------------

--
-- Structure de la table `sponsoring`
--

CREATE TABLE `sponsoring` (
  `id` int(11) NOT NULL,
  `budget` double NOT NULL,
  `datesponsoring` date NOT NULL,
  `type` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `sponsoring`
--

INSERT INTO `sponsoring` (`id`, `budget`, `datesponsoring`, `type`) VALUES
(1, 250, '2019-01-01', 'Sponsoring financier'),
(2, 500, '2019-01-01', 'Sponsoring educatif');

-- --------------------------------------------------------

--
-- Structure de la table `transaction`
--

CREATE TABLE `transaction` (
  `id` int(11) NOT NULL,
  `id_account_id` int(11) NOT NULL,
  `montant` double NOT NULL,
  `account_debited` varchar(255) NOT NULL,
  `account_destination` varchar(255) NOT NULL,
  `date_transaction` varchar(255) NOT NULL,
  `type_transaction` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `transaction`
--

INSERT INTO `transaction` (`id`, `id_account_id`, `montant`, `account_debited`, `account_destination`, `date_transaction`, `type_transaction`, `description`) VALUES
(1, 1, 10000, '564261878', '5641652649', '2003-10-12', 'collecte', 'chient'),
(2, 1, 9999, '78971465', '5641652649', '2003-10-12', 'autre', 'chient'),
(3, 1, 45000, 'HHHHHHHHHHHHHHHHH', 'JHZEFZHFVJVFJRVFJGR', '2003-10-12', 'pret', 'chient');

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `dtb` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `state` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  `image` varchar(255) NOT NULL,
  `sexe` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`id`, `name`, `lastname`, `dtb`, `phone`, `email`, `address`, `password`, `state`, `role`, `image`, `sexe`) VALUES
(1, 'selim', 'gaaloul', '2003-06-13', '25487965', 'selim@gmail.com', 'Nabeul', '$2y$10$hNzY5U9TFsqG/I0nSZNvtOCxcGmkwoMMpJY3UAC3TL1ryRiIUtvd.', '0', '1', 'C:\nmpp\\htdocsa40\\GivestC2\\public\\backOffice\\images\\uploads\\logouser.png', '1'),
(3, 'sami', 'gaaloul', '2003-06-13', '25487965', 'sami@gmail.com', 'Tunis', '$2y$10$xg31t.tVfG68yvG3uxZGu.AH3qUmqmtg2ldmj1.rPQRtQIdDFcBYu', '1', '0', '1c792d152026d2a81396bc8a6475049c.jpg', '1'),
(5, 'Gaaloul', 'Selim', '2003-06-13', '50111079', 'selim03@gmail.com', 'Hammamet', 'slouma123', '0', '1', 'c://image.jpg', '1'),
(6, 'mokh', 'gg', '578525', '50111079', 'mokhgmail.com', 'hamma', 'mokh', '1', '1', '/xampp/htdocs/Image/ahmed.png', '1'),
(7, 'kehia', 'khalil', '130603', '5000000', 'kk@gmail.com', 'babsadoun', 'kaka', '1', '1', '/xampp/htdocs/Image/Calque%202.png', 'homme'),
(8, 'yy', 'yy', '2024-04-11', '12345678', 'yy@hmaol.com', 'hhh', '$2a$12$iDRs70M.tauPIzJkGE9EheUrBqkMfCM2FV7SsKgelkf8.j/w7moKS', '0', '0', '', '0'),
(9, 'ff', 'ff', '2024-04-11', '12345678', 'ff@fmom.com', 'ffff', '$2a$12$muCesTSMITN54mnuSvTeT.fEzVHkT5Yx82DO30.o3WsZayZD1plmG', '0', '0', '', '1'),
(10, 'Lauren', 'Hirt', '2024-04-05', '50111079', 'lo@gmail.com', 'geneve', '$2a$12$0z2883wBdD1rmB0hilu.O.WS3OCWHIgdb88eQuVHMTyveXhua9l.u', '1', '1', '', '0'),
(11, 'hamza', 'Kaabar', '2024-04-11', '12345678', 'hamza@gmail.com', 'hammamet', '$2a$12$PUypdHqWoKsjl1ym1cbJUe35.j47gnU7FlN1dpdEvj05AGwBUTJfW', '0', '1', '', '1'),
(12, 'Emna', 'Laribi', '2024-04-11', '12345678', 'emna@gmail.com', 'Hammamet', '$2a$12$dw75.jPh1jbEHwe4ziiegOGUJ9884z1tZh6TjSk81A2tAQJ.Wgifm', '0', '0', '', '0'),
(13, 'Zahra', 'Laribi', '2024-04-04', '50111079', 'zahra@gmail.com', 'hammamet', '$2a$12$vEbfA0f8NHNeEddAOc1Zwe3smtMYkOaefb.Vq5yogvORjWANIYQtS', '1', '0', '', '0'),
(14, 'mima', 'zohra', '2024-04-11', '50111079', 'mima@gmail.com', 'hammamet', '$2a$12$s4gC/rzdv1bWtdw5wifny.M0sioNnhgyHy.pkXKXTBBxH7/cBFy4G', '1', '0', '', '0'),
(15, 'xx', 'xx', '2024-04-05', '50111079', 'xx@gmail.com', 'hhhhh', '$2a$12$Dfd4LSHmoVC9hI7PLKTi7OZYrqpteId2W4yf8FQ/xOzgtSdOjRHW6', '0', '0', '', '0'),
(16, 'imen', 'talouch', '2024-04-02', '12345678', 'imen@gmail.com', 'nabeul', '$2a$12$Q02oPfhPMHqrhxtEtu7gk./t.TKk0YVKq.Wg3bg3oaM5./YynG8QS', '1', '0', '', '0'),
(17, 'ayoub', 'kh', '2024-04-02', '12345678', 'ayoub@gmail.com', 'tunis', '$2a$12$js/FKxVQ2aMbXgCcTqYEmuWQEWm0wa6S/KyhqcbCZKX.D9DsmTYTS', '1', '0', '', '1'),
(18, 'kapo', 'kapo', '2024-04-04', '87654321', 'kapo@gmail.com', 'hammamet', '$2a$12$xIefP6W7CnYburGj1dyh1uzNwVjk/6fOIF83BbDIim.hkCGbOMicm', '1', '0', '', '1'),
(19, 'halouma', 'kehia', '2024-04-01', '12345678', 'hallouma@gmail.com', 'paris', '$2a$12$OlkCpJXEBQh83yN5TjFJGurtwWBx5yc0t0EwZGmEcF9it0NLjxIT.', '1', '0', '', '0'),
(20, 'sonia', 'gaaloul', '2024-04-03', '96385274', 'sonia@gmail.com', 'nabeul', '$2a$12$09YKuA3l6.uOJnFx6LfBy.RjKhL2tGYzVNZSQ4Nb.O1RFp75o8Wcq', '1', '0', '', '0'),
(21, 'meher', 'laribi', '2024-04-10', '65498732', 'meher@gmail.com', 'hammamet', '$2a$12$TYgkCqTjPILdWQ9nv3Hnkus0n.NWIKSFWh8WyBKqa5rBC1NIEq0LS', '1', '0', '', '1'),
(22, 'yahya', 'kehia', '2024-04-18', '45685698', 'yahya@gmail.com', 'ariana', '$2a$12$mIbdDXwRmTtTo.MFcd2GQesfWGW8gLrpb8ZF8frPNxryY71Vzoiqm', '1', '0', '', '1'),
(23, 'lalla', 'lalla', '2024-04-03', '12345678', 'lalla@gmail.com', 'sfax', '$2a$12$H098WSlD.9bzx0k9gfHjIOCSWF1cIml3thJcrLCcCWud1N7Sokwgu', '0', '0', 'C:\\Users\\21650\\Desktop\\3A40\\semaistre 2\\pidevJava\\workshopjdbc\\src\\main\\resources\\images\\selfie.jpg', '1'),
(24, 'hedi', 'gaaloul', '2024-04-06', '12345678', 'hedi@gmail.com', 'tunis', '$2a$12$XNw8DEmULfN1JVge7ozUT.1hakykDPhA4g7SHdZ7PGJAvBmCADgnS', '1', '0', 'C:\\Users\\21650\\Desktop\\3A40\\semaistre 2\\pidevJava\\workshopjdbc\\src\\main\\resources\\images\\selfie24.jpg', '1'),
(25, 'zied', 'gaaloul', '2024-04-05', '78965487', 'zied@gmail.com', 'nabeul', '$2a$12$CYxPaBmHvXFy7XRlCfM4sOyDmyNbKyXFmvhMP7cUBMrNyrmiZ6amu', '1', '0', 'C:\\Users\\21650\\Desktop\\3A40\\semaistre 2\\pidevJava\\workshopjdbc\\src\\main\\resources\\images\\selfie25.jpg', '1'),
(31, 'yoyo', 'yoyo', '2024-04-04', '12345698', 'yo@gmail.com', 'tunis', '$2a$12$LeE987lta3Hazg71Moyff.5g/rXvQczpXXsi7oiMLr8xJ/aUZm.Zu', '1', '0', 'images/IMG_2669.JPG', '1'),
(33, 'hatem', 'gaaloul', '2024-04-04', '98765432', 'hatem@gmail.com', 'zurich', '$2a$12$SRSrKGGMhB6wrwMk.NUp6.zycYCJnN6nZYU0Is8AZnrzTAJ/VmXk2', '1', '0', 'images/selfie33.JPG', '1');

-- --------------------------------------------------------

--
-- Structure de la table `walet`
--

CREATE TABLE `walet` (
  `id` int(11) NOT NULL,
  `idofuser_id` int(11) DEFAULT NULL,
  `lastconnection` varchar(255) NOT NULL,
  `nbrconnection` int(11) NOT NULL,
  `rate` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `walet`
--

INSERT INTO `walet` (`id`, `idofuser_id`, `lastconnection`, `nbrconnection`, `rate`) VALUES
(1, 13, '2024-04-27', 0, 0),
(2, 14, '2024-04-27', 0, 0),
(3, 15, '2024-04-28', 0, 0),
(4, 16, '2024-04-28', 0, 0),
(5, 17, '2024-04-28', 0, 0),
(6, 18, '2024-04-28', 0, 0),
(7, 19, '2024-04-28', 0, 0),
(8, 20, '2024-04-28', 0, 0),
(9, 21, '2024-04-28', 0, 0),
(10, 22, '2024-04-28', 0, 0),
(11, 23, '2024-04-28', 0, 0),
(12, 24, '2024-04-29', 0, 0),
(13, 25, '2024-04-29', 0, 0),
(19, 31, '2024-04-30', 4, 1),
(21, 33, '2024-04-30', 12, 5);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UNIQ_7D3656A4D2DF75A3` (`id_association_id`);

--
-- Index pour la table `action`
--
ALTER TABLE `action`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_47CC8C92E597EC3B` (`id_association`);

--
-- Index pour la table `association`
--
ALTER TABLE `association`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_FD8521CC6B3CA4B` (`id_user`);

--
-- Index pour la table `demande`
--
ALTER TABLE `demande`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_2694D7A579F37AE5` (`id_user_id`),
  ADD KEY `IDX_2694D7A5C5FCD93B` (`id_pret_id`);

--
-- Index pour la table `demande_sponsoring`
--
ALTER TABLE `demande_sponsoring`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_1295AFAFD4825EF9` (`idsponsoring_id`),
  ADD KEY `IDX_1295AFAFA76ED395` (`user_id`);

--
-- Index pour la table `doctrine_migration_versions`
--
ALTER TABLE `doctrine_migration_versions`
  ADD PRIMARY KEY (`version`);

--
-- Index pour la table `messenger_messages`
--
ALTER TABLE `messenger_messages`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_75EA56E0FB7336F0` (`queue_name`),
  ADD KEY `IDX_75EA56E0E3BD61CE` (`available_at`),
  ADD KEY `IDX_75EA56E016BA31DB` (`delivered_at`);

--
-- Index pour la table `participant`
--
ALTER TABLE `participant`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_D79F6B11BEF37782` (`idseminar_id`);

--
-- Index pour la table `pret`
--
ALTER TABLE `pret`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `seminar`
--
ALTER TABLE `seminar`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_BFFD2C88238C0A39` (`iduserasso_id`);

--
-- Index pour la table `souvenir`
--
ALTER TABLE `souvenir`
  ADD PRIMARY KEY (`idsouvenir`);

--
-- Index pour la table `sponsoring`
--
ALTER TABLE `sponsoring`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_723705D13EE1DF6D` (`id_account_id`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `walet`
--
ALTER TABLE `walet`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UNIQ_654327374E56F9` (`idofuser_id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `account`
--
ALTER TABLE `account`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `action`
--
ALTER TABLE `action`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `association`
--
ALTER TABLE `association`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT pour la table `demande`
--
ALTER TABLE `demande`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `demande_sponsoring`
--
ALTER TABLE `demande_sponsoring`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `messenger_messages`
--
ALTER TABLE `messenger_messages`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `participant`
--
ALTER TABLE `participant`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT pour la table `pret`
--
ALTER TABLE `pret`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `seminar`
--
ALTER TABLE `seminar`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `souvenir`
--
ALTER TABLE `souvenir`
  MODIFY `idsouvenir` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT pour la table `sponsoring`
--
ALTER TABLE `sponsoring`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `transaction`
--
ALTER TABLE `transaction`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT pour la table `walet`
--
ALTER TABLE `walet`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `account`
--
ALTER TABLE `account`
  ADD CONSTRAINT `FK_7D3656A4D2DF75A3` FOREIGN KEY (`id_association_id`) REFERENCES `association` (`id`);

--
-- Contraintes pour la table `action`
--
ALTER TABLE `action`
  ADD CONSTRAINT `FK_47CC8C92E597EC3B` FOREIGN KEY (`id_association`) REFERENCES `association` (`id`);

--
-- Contraintes pour la table `association`
--
ALTER TABLE `association`
  ADD CONSTRAINT `FK_FD8521CC6B3CA4B` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `demande`
--
ALTER TABLE `demande`
  ADD CONSTRAINT `FK_2694D7A579F37AE5` FOREIGN KEY (`id_user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FK_2694D7A5C5FCD93B` FOREIGN KEY (`id_pret_id`) REFERENCES `pret` (`id`);

--
-- Contraintes pour la table `demande_sponsoring`
--
ALTER TABLE `demande_sponsoring`
  ADD CONSTRAINT `FK_1295AFAFA76ED395` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FK_1295AFAFD4825EF9` FOREIGN KEY (`idsponsoring_id`) REFERENCES `sponsoring` (`id`);

--
-- Contraintes pour la table `participant`
--
ALTER TABLE `participant`
  ADD CONSTRAINT `FK_D79F6B11BEF37782` FOREIGN KEY (`idseminar_id`) REFERENCES `seminar` (`id`);

--
-- Contraintes pour la table `seminar`
--
ALTER TABLE `seminar`
  ADD CONSTRAINT `FK_BFFD2C88238C0A39` FOREIGN KEY (`iduserasso_id`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `transaction`
--
ALTER TABLE `transaction`
  ADD CONSTRAINT `FK_723705D13EE1DF6D` FOREIGN KEY (`id_account_id`) REFERENCES `account` (`id`);

--
-- Contraintes pour la table `walet`
--
ALTER TABLE `walet`
  ADD CONSTRAINT `FK_654327374E56F9` FOREIGN KEY (`idofuser_id`) REFERENCES `user` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
