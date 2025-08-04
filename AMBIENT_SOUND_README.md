# Ambient Sound Feature

## Overview
The Ambient Sound feature provides users with calming soundscapes to help them relax and focus. The interface includes a main player for the currently playing sound and a grid of sound cards for easy selection.

## Features

### 1. Main Player Section
- **Circular Background Image**: Displays the current sound's visual representation
- **Sound Title**: Shows the name of the currently playing sound
- **Large Play/Pause Button**: Circular blue button with play/pause functionality
- **Progress Bar**: Shows current playback time (2:15) and total duration (15:00)
- **Volume Control**: Slider with speaker icon for volume adjustment

### 2. Category Filter
- **Horizontal Scrollable Categories**: All, Nature, White Noise, Meditation
- **Visual Selection**: Selected category has blue background with white text
- **Unselected Categories**: White background with dark text

### 3. Sound Cards Grid
- **2-Column Layout**: Responsive grid of sound cards
- **Color-Coded Cards**: Each sound has a unique pastel background color
- **Card Information**: Title and duration/loop status
- **Play Button**: Small circular play button on each card

### 4. Bottom Navigation
- **Favorite Button**: Heart icon for favoriting sounds
- **Shuffle Button**: Shuffle icon for random playback
- **Repeat Button**: Repeat icon for loop functionality

## Sound Cards Available

1. **Ocean Waves** - 15 min (Light Blue: #DBEAFE)
2. **Forest Birds** - Loop (Light Green: #D1FAE5)
3. **White Noise** - Loop (Light Grey: #F3F4F6)
4. **Fireplace** - 30 min (Light Orange: #FEE2E2)
5. **Night Rain** - Loop (Light Purple: #EDE9FE)
6. **Mountain Stream** - 20 min (Medium Blue: #BFDBFE)

## Color Scheme

### Background Colors
- **Main Background**: #F0F8FF (Very light blue/off-white)
- **Card Backgrounds**: Various pastel colors for each sound type
- **Selected Category**: #DBEAFE (Light blue)
- **Unselected Categories**: #FFFFFF (White)

### Text Colors
- **Primary Text**: #1F2937 (Dark grey)
- **Muted Text**: #6B7280 (Medium grey)
- **Selected Category Text**: #FFFFFF (White)

### Interactive Elements
- **Play Button**: #DBEAFE (Light blue)
- **Progress Bar**: #DBEAFE (Light blue)
- **Volume Slider**: #DBEAFE (Light blue)

## Files Created/Modified

### Layout Files
- `activity_ambient.xml` - Main ambient sound screen layout
- `sound_card_item.xml` - Individual sound card layout

### Kotlin Files
- `ambient.kt` - Main activity with full functionality

### Drawable Resources
- `circle_blue.xml` - Circular blue background for play button
- `ic_play_triangle.xml` - Play triangle icon
- `ic_pause.xml` - Pause icon

### Color Resources
- All colors defined in `colors.xml`

## Implementation Details

### Activity Features
- **Play/Pause Toggle**: Main play button switches between play and pause icons
- **Category Selection**: Clicking categories updates visual selection
- **Sound Card Interaction**: Clicking cards triggers sound playback
- **Progress Tracking**: SeekBar for playback progress
- **Volume Control**: SeekBar for volume adjustment
- **Navigation**: Back button and bottom navigation controls

### RecyclerView Implementation
- **GridLayoutManager**: 2-column grid layout
- **Custom Adapter**: SoundCardsAdapter with ViewHolder pattern
- **Dynamic Colors**: Each card gets its unique background color
- **Click Handling**: OnItemClick callback for sound selection

### UI Components
- **ConstraintLayout**: Main layout with proper constraints
- **LinearLayout**: Organized sections for header, player, and navigation
- **HorizontalScrollView**: Scrollable category selection
- **CardView**: Rounded corner cards with elevation
- **SeekBar**: Progress and volume controls
- **ImageView**: Icons and play buttons

## Usage

1. **Launch Activity**: Navigate to the ambient sound screen
2. **Select Sound**: Tap any sound card to start playback
3. **Control Playback**: Use the main play/pause button
4. **Adjust Volume**: Use the volume slider
5. **Filter Sounds**: Select categories to filter available sounds
6. **Navigation**: Use bottom buttons for additional controls

## Future Enhancements

- **Audio Implementation**: Integrate MediaPlayer or ExoPlayer for actual sound playback
- **Favorites System**: Implement favorite functionality
- **Shuffle/Repeat**: Add shuffle and repeat modes
- **Sound Mixing**: Allow multiple sounds to play simultaneously
- **Timer Functionality**: Add sleep timer for automatic stop
- **Download Sounds**: Offline sound storage
- **Custom Sounds**: User-uploaded ambient sounds

## Dependencies

The implementation uses standard Android components:
- `androidx.constraintlayout.widget.ConstraintLayout`
- `androidx.recyclerview.widget.RecyclerView`
- `androidx.cardview.widget.CardView`
- `androidx.appcompat.app.AppCompatActivity`

No additional external dependencies are required for the UI implementation. 