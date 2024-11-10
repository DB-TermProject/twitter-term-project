package org.example.ui.page;

import org.example.domain.user.service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.sql.Connection;


public class HomeFeedPage_copy extends JFrame {
    private final UserService userService = new UserService();
    private final Connection connection; // Connection 필드 추가

    public HomeFeedPage_copy(Connection con) {
        this.connection = con; // Connection 초기화
        initializeFrame();
        JPanel mainPanel = createMainPanel();
        add(mainPanel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeFrame() {
        setTitle("Twitter");
        setSize(450, 700);  // 전체 크기 조정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // 1. 헤더 영역 (트위터 로고)
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);

        // 2. 피드 영역 (스크롤 가능한 트윗들)
        mainPanel.add(createScrollableFeedPanel(), BorderLayout.CENTER);

        // 3. 마이크로블로그 영역 (하단 네비게이션)
        mainPanel.add(createMicroblogPanel(), BorderLayout.SOUTH);

        return mainPanel;
    }

    private JPanel createHeaderPanel() {
        // 헤더 패널 생성 및 기본 설정
        JPanel headerPanel = new JPanel(new BorderLayout());  // BorderLayout으로 레이아웃 설정
        headerPanel.setBackground(Color.WHITE);  // 배경색을 흰색으로 설정
        headerPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));  // 연한 회색 테두리 추가

        // 왼쪽에 위치할 트위터 로고와 텍스트를 담을 패널
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));  // 왼쪽 정렬된 FlowLayout 사용
        leftPanel.setBackground(Color.WHITE);  // 배경색을 흰색으로 설정

        try {
            // 트위터 로고 이미지 로드 및 설정
            ImageIcon logoIcon = new ImageIcon("src/main/java/org/example/asset/twitter.png");  // 로고 이미지 파일 로드
            Image scaledLogo = logoIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);  // 로고 크기를 20x20으로 조정
            JLabel logoLabel = new JLabel(new ImageIcon(scaledLogo));  // 조정된 로고 이미지를 라벨에 설정

            // "Twitter" 텍스트 라벨 생성 및 폰트 설정
            JLabel titleLabel = new JLabel("Twitter");
            titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));  // 맑은 고딕 폰트, 굵게, 크기 14로 설정

            // 로고와 텍스트를 왼쪽 패널에 추가
            leftPanel.add(logoLabel);
            leftPanel.add(titleLabel);
        } catch (Exception e) {
            System.out.println("로고 로드 실패");  // 로고 이미지 로드 실패 시 에러 메시지 출력
        }

        // 완성된 왼쪽 패널을 헤더 패널의 서쪽(왼쪽)에 배치
        headerPanel.add(leftPanel, BorderLayout.WEST);
        return headerPanel;
    }

    private JScrollPane createScrollableFeedPanel() {
        // 피드를 담을 메인 패널 생성
        JPanel feedPanel = new JPanel();
        feedPanel.setLayout(new BoxLayout(feedPanel, BoxLayout.Y_AXIS));
        feedPanel.setBackground(Color.WHITE);

        try {
            // 데이터베이스에서 피드 개수 확인
            //userService.getFeedCount(connection);  // UserService에 getFeedCount 메소드 필요
            int feedCount = 10;// 지금은 임의로 10

            if (feedCount > 0) {
                // 피드가 있는 경우
                for (int i = 0; i < feedCount; i++) {
                    JPanel tweetPanel = createTweetPanel();
                    tweetPanel.setMaximumSize(new Dimension(400, 150));
                    tweetPanel.setPreferredSize(new Dimension(400, 150));
                    feedPanel.add(tweetPanel);
                    feedPanel.add(Box.createVerticalStrut(1));
                }
            } else {
                // 피드가 없는 경우 표시할 패널
                JPanel emptyFeedPanel = createEmptyFeedPanel();
                feedPanel.add(emptyFeedPanel);
            }
        } catch (Exception e) {
            System.out.println("피드 로드 실패: " + e.getMessage());
            e.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(feedPanel);
        scrollPane.setPreferredSize(new Dimension(350, 500));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);

        return scrollPane;
    }

    private JPanel createTweetPanel() {
        // 트윗 패널의 기본 설정
        JPanel tweetPanel = new JPanel(new BorderLayout(10, 0));  // 10픽셀의 수평 간격
        tweetPanel.setBackground(Color.WHITE);  // 배경색 흰색
        // 테두리 설정: 회색 테두리와 내부 여백
        tweetPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // 왼쪽 프로필 이미지 영역
        JPanel profilePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        profilePanel.setBackground(Color.WHITE);
        try {
            // 프로필 이미지 로드
            ImageIcon profileIcon = new ImageIcon("src/main/java/org/example/asset/profileImage.png");
            if (profileIcon.getIconWidth() > 0) {
                // 이미지가 정상적으로 로드된 경우
                Image scaledImage = profileIcon.getImage();
                ImageIcon roundedIcon = createRoundedIcon(scaledImage, 40);  // 원형 프로필 이미지 생성
                profilePanel.add(new JLabel(roundedIcon));
            } else {
                // 이미지 로드 실패 시 기본 회색 원형 이미지 생성
                BufferedImage defaultImage = new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = defaultImage.createGraphics();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.LIGHT_GRAY);
                g2.fillOval(0, 0, 39, 39);
                g2.dispose();
                profilePanel.add(new JLabel(new ImageIcon(defaultImage)));
            }
        } catch (Exception e) {
            System.out.println("프로필 이미지 로드 실패: " + e.getMessage());
            e.printStackTrace();
        }

        // 오른쪽 컨텐츠 영역 (사용자 정보, 트윗 내용, 상호작용 버튼)
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);

        // 사용자 정보 영역 (이름, 핸들)
        JPanel userInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        userInfoPanel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel("사용자 이름");
        nameLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));

        JLabel handleLabel = new JLabel("@handle · 2h");
        handleLabel.setForeground(Color.GRAY);

        userInfoPanel.add(nameLabel);
        userInfoPanel.add(handleLabel);

        // 트윗 내용 텍스트
        JLabel tweetContent = new JLabel(
                "<html><body style='width: 240px'>" +
                        "여기에 트윗 내용이 들어갑니다. 긴 내용의 텍스트도 자동으로 줄바꿈이 됩니다. " +
                        "이렇게 길게 작성해도 패널의 크기에 맞춰서 자동으로 줄바꿈이 됩니다." +
                        "</body></html>"
        );

        // 하단 상호작용 아이콘 영역 (댓글, 좋아요)
        JPanel interactionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        interactionPanel.setBackground(Color.WHITE);
        interactionPanel.setName("interactionPanel");
        interactionPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 0));  // 왼쪽 여백 40픽셀

        // 댓글 아이콘 패널
        JPanel commentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        commentPanel.setBackground(Color.WHITE);
        commentPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 75));  // 댓글과 하트 사이 간격
        commentPanel.add(createInteractionLabel("comment.png", "0"));

        // 하트 아이콘 패널
        JPanel heartPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        heartPanel.setBackground(Color.WHITE);
        heartPanel.add(createInteractionLabel("heart.png", "0"));

        // 상호작용 패널에 댓글과 하트 추가
        interactionPanel.add(commentPanel);
        interactionPanel.add(heartPanel);

        // 컨텐츠 요소들 왼쪽 정렬
        tweetContent.setAlignmentX(Component.LEFT_ALIGNMENT);
        userInfoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        interactionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // 컨텐츠 패널에 모든 요소 추가
        contentPanel.add(userInfoPanel);
        contentPanel.add(Box.createVerticalStrut(5));  // 5픽셀 수직 간격
        contentPanel.add(tweetContent);
        contentPanel.add(Box.createVerticalStrut(10));  // 10픽셀 수직 간격
        contentPanel.add(interactionPanel);

        // 최종 조립
        tweetPanel.add(profilePanel, BorderLayout.WEST);
        tweetPanel.add(contentPanel, BorderLayout.CENTER);

        // 트윗 패널 크기 설정
        tweetPanel.setMaximumSize(new Dimension(350, 200));
        tweetPanel.setPreferredSize(new Dimension(350, 200));

        // 트윗 클릭 이벤트 처리
        tweetPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 상호작용 패널이 아닌 영역 클릭 시 상세 페이지로 이동
                if (!isClickedOnInteractionPanel(e.getPoint(), tweetPanel)) {
                    setVisible(false);
                    SwingUtilities.invokeLater(() -> {
                        TweetDetailPage detailPage = new TweetDetailPage(
                                connection,
                                nameLabel.getText(),
                                handleLabel.getText(),
                                tweetContent.getText().replaceAll("<html><body style='width: 240px'>|</body></html>", ""),
                                "src/main/java/org/example/asset/profileImage.png",
                                "2h",
                                "0",
                                "0"
                        );
                        detailPage.setVisible(true);
                    });
                }
            }

            // 마우스 커서 변경 처리
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!isClickedOnInteractionPanel(e.getPoint(), tweetPanel)) {
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        return tweetPanel;
    }

    private boolean isClickedOnInteractionPanel(Point clickPoint, JPanel tweetPanel) {
        // 트윗 패널의 모든 컴포넌트를 가져옴
        Component[] components = tweetPanel.getComponents();

        // 각 컴포넌트를 순회
        for (Component component : components) {
            // 컴포넌트가 JPanel인 경우에만 처리
            if (component instanceof JPanel) {
                JPanel contentPanel = (JPanel) component;
                // 해당 패널의 모든 하위 컴포넌트를 가져옴
                Component[] contentComponents = contentPanel.getComponents();

                // 하위 컴포넌트들을 순회
                for (Component c : contentComponents) {
                    // 컴포넌트가 JPanel이고, 이름이 "interactionPanel"인 경우
                    if (c instanceof JPanel && c.getName() != null && c.getName().equals("interactionPanel")) {
                        // 클릭 포인트를 상호작용 패널의 좌표계로 변환
                        Point relativePoint = SwingUtilities.convertPoint(tweetPanel, clickPoint, c);
                        // 변환된 포인트가 상호작용 패널 내부에 있는지 확인
                        return c.contains(relativePoint);
                    }
                }
            }
        }
        // 상호작용 패널을 찾지 못했거나 클릭 위치가 패널 외부인 경우
        return false;
    }

    private JPanel createMicroblogPanel() {
        // 하단 네비게이션 바를 위한 패널 생성
        JPanel microblogPanel = new JPanel();

        // 레이아웃 설정
        // FlowLayout.CENTER: 아이콘들을 중앙 정렬
        // 40: 아이콘 간의 수평 간격
        // 10: 아이콘의 수직 간격
        microblogPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 10));

        // 배경색을 흰색으로 설정
        microblogPanel.setBackground(Color.WHITE);

        // 상단에만 연한 회색 테두리 추가 (1픽셀 두께)
        microblogPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));

        // 네비게이션 바의 크기 설정 (너비 450, 높이 60)
        microblogPanel.setPreferredSize(new Dimension(450, 60));

        // 네비게이션 아이콘들 추가
        microblogPanel.add(createIconLabel("home.png"));     // 홈 아이콘
        microblogPanel.add(createIconLabel("follow.png"));   // 팔로우 아이콘
        microblogPanel.add(createIconLabel("alarm.png"));    // 알림 아이콘
        microblogPanel.add(createIconLabel("user.png"));     // 사용자 프로필 아이콘

        return microblogPanel;
    }

    private ImageIcon createRoundedIcon(Image image, int diameter) {
        // 원형 이미지를 그리기 위한 빈 버퍼 이미지 생성
        // TYPE_INT_ARGB: 투명도를 지원하는 이미지 타입
        BufferedImage rounded = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = rounded.createGraphics();

        // 이미지 품질 향상을 위한 설정
        // 안티앨리어싱 활성화로 부드러운 곡선 표현
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 렌더링 품질 향상 설정
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // 원형 마스크 생성 과정
        // 1. 전체 영역을 투명하게 설정
        g2.setComposite(AlphaComposite.Clear);
        g2.fillRect(0, 0, diameter, diameter);

        // 2. 흰색 원 그리기
        g2.setComposite(AlphaComposite.Src);
        g2.setColor(Color.WHITE);
        g2.fillOval(0, 0, diameter-1, diameter-1);

        // 3. 원형 마스크 위에 실제 이미지 그리기
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(image, 0, 0, diameter-1, diameter-1, null);

        // 4. 회색 테두리 추가
        g2.setComposite(AlphaComposite.SrcOver);
        g2.setColor(Color.LIGHT_GRAY);
        g2.setStroke(new BasicStroke(1));  // 테두리 두께 1픽셀
        g2.drawOval(0, 0, diameter-2, diameter-2);

        // 그래픽스 자원 해제
        g2.dispose();

        // 완성된 원형 이미지를 아이콘으로 변환하여 반환
        return new ImageIcon(rounded);
    }

    private JLabel createIconLabel(String iconName) {
        try {
            // 원본 아이콘 이미지 로드
            ImageIcon originalIcon = new ImageIcon("src/main/java/org/example/asset/" + iconName);

            // 이미지 크기를 25x25 픽셀로 조정
            // SCALE_SMOOTH: 부드러운 이미지 스케일링 적용
            Image scaledImage = originalIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);

            // 크기 조정된 이미지로 라벨 생성
            JLabel iconLabel = new JLabel(new ImageIcon(scaledImage));

            // 라벨의 선호 크기를 40x40으로 설정 (아이콘 주변에 여백 생성)
            iconLabel.setPreferredSize(new Dimension(40, 40));

            // 마우스 이벤트 처리
            iconLabel.addMouseListener(new MouseAdapter() {
                // 마우스가 아이콘 위로 올라갔을 때
                @Override
                public void mouseEntered(MouseEvent e) {
                    setCursor(new Cursor(Cursor.HAND_CURSOR));  // 손가락 모양 커서로 변경
                }

                // 마우스가 아이콘에서 벗어났을 때
                @Override
                public void mouseExited(MouseEvent e) {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));  // 기본 커서로 복귀
                }
            });

            return iconLabel;
        } catch (Exception e) {
            // 이미지 로드 실패 시 에러 메시지 출력 및 빈 라벨 반환
            System.out.println("아이콘 로드 실패: " + iconName);
            return new JLabel();
        }
    }

    private JPanel createInteractionLabel(String iconName, String count) {
        // 아이콘과 카운트를 담을 컨테이너 패널 생성
        JPanel container = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        container.setBackground(Color.WHITE);

        try {
            // 아이콘 이미지 로드 및 크기 조정 (18x18 픽셀)
            ImageIcon icon = new ImageIcon("src/main/java/org/example/asset/" + iconName);
            Image scaledImage = icon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            JLabel iconLabel = new JLabel(new ImageIcon(scaledImage));

            // 카운트 레이블 생성 및 회색으로 설정
            JLabel countLabel = new JLabel(count);
            countLabel.setForeground(Color.GRAY);

            // 하트(좋아요) 아이콘 처리
            if (iconName.equals("heart.png")) {
                final boolean[] isLiked = {false};  // 좋아요 상태를 추적하는 플래그

                MouseAdapter heartListener = new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (!isLiked[0]) {  // 아직 좋아요하지 않은 경우에만
                            // 카운트 증가
                            int currentCount = Integer.parseInt(countLabel.getText());
                            countLabel.setText(String.valueOf(currentCount + 1));
                            isLiked[0] = true;  // 좋아요 상태로 변경
                        }
                    }

                    // 마우스 호버 효과
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        setCursor(new Cursor(Cursor.HAND_CURSOR));  // 손가락 커서로 변경
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));  // 기본 커서로 복귀
                    }
                };

                // 아이콘과 카운트 레이블에 이벤트 리스너 추가
                iconLabel.addMouseListener(heartListener);
                countLabel.addMouseListener(heartListener);
            }
            // 댓글 아이콘 처리
            else if (iconName.equals("comment.png")) {
                MouseAdapter commentListener = new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        System.out.println("댓글 아이콘이 클릭되었습니다.");
                        // 추후 댓글 창으로 이동하는 기능 구현 예정
                    }

                    // 마우스 호버 효과
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        setCursor(new Cursor(Cursor.HAND_CURSOR));
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    }
                };

                // 아이콘과 카운트 레이블에 이벤트 리스너 추가
                iconLabel.addMouseListener(commentListener);
                countLabel.addMouseListener(commentListener);
            }

            // 컨테이너에 아이콘과 카운트 추가
            container.add(iconLabel);
            container.add(countLabel);

        } catch (Exception e) {
            System.out.println("상호작용 아이콘 로드 실패: " + iconName);
        }

        return container;
    }
    // 피드가 없을 때 표시할 패널 생성 메소드
    private JPanel createEmptyFeedPanel() {
        JPanel emptyPanel = new JPanel();
        emptyPanel.setLayout(new BoxLayout(emptyPanel, BoxLayout.Y_AXIS));
        emptyPanel.setBackground(Color.WHITE);
        emptyPanel.setBorder(BorderFactory.createEmptyBorder(100, 20, 0, 20));

        // 메시지 텍스트
        JLabel messageLabel = new JLabel("아직 작성된 트윗이 없습니다");
        messageLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        messageLabel.setForeground(Color.GRAY);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 부가 설명 텍스트
        JLabel subMessageLabel = new JLabel("첫 번째 트윗을 작성해보세요!");
        subMessageLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        subMessageLabel.setForeground(Color.GRAY);
        subMessageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 컴포넌트 추가
        emptyPanel.add(messageLabel);
        emptyPanel.add(Box.createVerticalStrut(10));  // 간격 추가
        emptyPanel.add(subMessageLabel);

        return emptyPanel;
    }
}